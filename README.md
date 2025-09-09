# Notification Service

Service responsible for consuming appointment events and sending email notifications to patients. It listens to the fanout exchange published by Appointment Service, generates HTML emails using Thymeleaf templates, persists basic email metadata, and runs a scheduled cleanup job for obsolete records.

Part of the hospital_app microservices system.


## Tech stack
- Java 21, Spring Boot 3.5
- Spring Mail (JavaMailSender)
- Thymeleaf templates for HTML emails
- Resilience4j retry for email sending
- Spring Data JPA (PostgreSQL)
- RabbitMQ consumer with TLS (JSON messages)
- Spring Scheduling (for maintenance job)
- Hexagonal architecture (ports and adapters)


## Architecture highlights
- Inbound messaging adapter:
  - RabbitMQ listener that consumes AppointmentMessage events from the fanout exchange and maps them to AppointmentEmail domain.
  - Class: infra/adapter/out/message/AppointmentNotificationQueueConsumerImpl
- Outbound adapters:
  - Email sender: wraps JavaMailSender with retry and uses Thymeleaf to render templates.
    - New appointment: infra/adapter/out/email/NewAppointmentEmailSenderImpl
    - Update appointment: infra/adapter/out/email/UpdateAppointmentEmailSender
    - Low-level sender with retry: infra/adapter/out/email/EmailSenderImpl (@Retry using resilience4j)
  - Persistence: JPA entity and repository to store email metadata of the last sent email per appointment.
    - Entity/table: infra/adapter/out/db/jpa/JpaAppointmentEmailEntity (table appointments_email)
    - Repository: infra/adapter/out/db/jpa/JpaAppointmentEmailRepository and JpaCustomAppointmentEmailRepository
- Scheduling:
  - Daily maintenance job to remove obsolete email records.
  - Class: infra/adapter/in/RemoveObsoleteAppointmentEmailJob (cron: 0 0 3 * * ?)
- Messaging topology:
  - Exchange: appointment.exchange (fanout)
  - Queue (bound to the exchange): notification.queue
  - Payload: com.hospital_app.common.message.dto.AppointmentMessage (JSON)


## Running locally
There are two common ways to run this service.

1) Using Docker Compose (recommended to run the whole system)
- Prerequisites: Docker and Docker Compose
- From the repo root, bring up dependencies and this service:
  docker compose up -d notification-service notification-service-db rabbitmq
- To trigger emails, run appointment-service as well (it publishes the messages):
  docker compose up -d appointment-service appointment-service-db
- Service container exposes port 8083, but there are no public HTTP endpoints (message-driven). You can still view logs and container health.
- PostgreSQL (service DB): localhost:5438 (mapped to container 5432)

2) Running via Maven locally
- Prerequisites: JDK 21, Maven, a running PostgreSQL and RabbitMQ with TLS, and SMTP credentials.
- Export env vars as in the root .env (see Environment variables) or create an application-local.yml.
- From the repo root (monorepo builds shared modules), run:
  mvn -q -DskipTests package
  mvn -q -pl notification_service -am spring-boot:run
- Service runs on port 8080 by default; in Docker it is mapped to 8083.

Remote debugging
- If ENABLE_REMOTE_DEBUG=true in Docker, Java debug port 5009 is exposed.


## Messaging
- Exchange: appointment.exchange (fanout)
- Queue: notification.queue
- Consumer flow:
  1) AppointmentNotificationQueueConsumerImpl receives AppointmentMessage.
  2) MessageAppointmentEmailMapper maps it to AppointmentEmail domain model.
  3) SendAppointmentEmailUseCase is executed:
     - If an email for the appointment already exists, an update email is sent and the stored record is updated.
     - Otherwise, a new appointment email is sent and stored.

Message fields used (subset): id (appointmentId), patientId, patientEmail, patientName, doctorId, doctorName, status, dateTime, notes, version.

RabbitMQ uses TLS with keystore/truststore configured via env vars. See rabbitmq/ in the repo root for cert materials and server configuration.


## Email sending
- SMTP server and credentials are configured via Spring Mail properties (from env vars).
- HTML templates:
  - templates/email/first_appointment_email.html
  - templates/email/update_appointment_email.html
- Resilience:
  - EmailSenderImpl is annotated with @Retry(name = "emailRetry"). Configuration (max attempts, wait duration, backoff) is defined under resilience4j in application.yml.

Notes for Gmail (if you use Gmail SMTP):
- Use an App Password (not your regular account password) when 2FA is enabled.
- Host: smtp.gmail.com, Port: 587, STARTTLS enabled.


## Persistence
- PostgreSQL via Spring Data JPA.
- Entity: infra/adapter/out/db/jpa/JpaAppointmentEmailEntity (appointments_email table)
- The entity tracks the latest state and updates sentAt automatically on create/update.
- Schema is created/updated according to HOSPITAL_APP_NOTIFICATION_SERVICE_DB_DDL_AUTO (default update in .env).


## Maintenance job
- RemoveObsoleteAppointmentEmailJob runs daily at 03:00 (cron 0 0 3 * * ?) invoking RemoveObsoleteAppointmentEmailUseCase to purge obsolete records.


## Environment variables
These are consumed by notification_service (see application.yml and docker-compose.yml). Defaults are in the root .env file.

Database
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_URL=jdbc:postgresql://notification-service-db:5432/notification_service_db
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_USER=user
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_PASS=pass
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_DRIVER=org.postgresql.Driver
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_DDL_AUTO=update
- HOSPITAL_APP_NOTIFICATION_SERVICE_DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect

RabbitMQ with TLS
- HOSPITAL_APP_RABBITMQ_HOST=rabbitmq
- HOSPITAL_APP_RABBITMQ_PORT=5671
- HOSPITAL_APP_RABBITMQ_USER=guest
- HOSPITAL_APP_RABBITMQ_PASS=guest
- HOSPITAL_APP_RABBITMQ_SSL_ENABLED=true
- HOSPITAL_APP_RABBITMQ_SSL_ALGO=TLSv1.2
- HOSPITAL_APP_RABBITMQ_SSL_VALIDATE=true
- HOSPITAL_APP_RABBITMQ_KEYSTORE=classpath:rabbitmq/client_keystore.p12
- HOSPITAL_APP_RABBITMQ_KEYSTORE_PASS=changeit
- HOSPITAL_APP_RABBITMQ_TRUSTSTORE=classpath:rabbitmq/truststore.p12
- HOSPITAL_APP_RABBITMQ_TRUSTSTORE_PASS=changeit

SMTP (Mail)
- HOSPITAL_APP_NOTIFICATION_SERVICE_MAIL_HOST=smtp.gmail.com
- HOSPITAL_APP_NOTIFICATION_SERVICE_MAIL_PORT=587
- HOSPITAL_APP_NOTIFICATION_SERVICE_MAIL_USER=your_email@example.com
- HOSPITAL_APP_NOTIFICATION_SERVICE_MAIL_PASS=your_app_password

Other
- ENABLE_REMOTE_DEBUG=true (when running in Docker, exposes port 5009)


## Useful commands
- Build monorepo: mvn -q -DskipTests package
- Run service only: mvn -q -pl notification_service -am spring-boot:run
- Start with dependencies via Docker:
  docker compose up -d notification-service notification-service-db rabbitmq appointment-service appointment-service-db
- View logs: docker logs -f hospital-app-notification-service


## Troubleshooting
- RabbitMQ TLS errors: ensure keystore/truststore paths and passwords match packaged resources and .env
- Emails not being sent:
  - Verify SMTP credentials and that STARTTLS (port 587) is allowed by your provider.
  - Check that appointment-service is running and publishing messages to the exchange.
  - Look for Resilience4j retry logs/fallback messages; repeated failures will trigger the fallback log.
- DB connection failures: confirm notification-service-db container is healthy and env vars are correct


## Project paths and references
- Dockerfile: notification_service/Dockerfile
- Spring config: notification_service/src/main/resources/application.yml
- Email templates: notification_service/src/main/resources/templates/email/
- RabbitMQ config: infra/config/message/rabbitmq/RabbitMQNotificationConfig.java
- Consumer: infra/adapter/out/message/AppointmentNotificationQueueConsumerImpl.java
- Email senders: infra/adapter/out/email/
- Persistence entity: infra/adapter/out/db/jpa/JpaAppointmentEmailEntity.java
- Wiring/beans: infra/config/bean/AppointmentEmailBean.java
- Maintenance job: infra/adapter/in/RemoveObsoleteAppointmentEmailJob.java


## License
This project is part of an educational/portfolio repository. See root-level LICENSE if available.
