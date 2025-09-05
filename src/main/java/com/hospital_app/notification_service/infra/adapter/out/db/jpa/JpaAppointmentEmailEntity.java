package com.hospital_app.notification_service.infra.adapter.out.db.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "appointments_email",
        indexes = {
                @Index(name = "idx_appointment_id", columnList = "appointment_id"),
        }
)
@Getter
@Setter
@NoArgsConstructor
public class JpaAppointmentEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID appointmentId;

    @NotNull
    private UUID patientId;

    @NotBlank
    @Email
    @Size(max = 100)
    private String patientEmail;

    @NotBlank
    @Size(min = 3, max = 255)
    private String patientName;

    @NotNull
    private UUID doctorId;

    @NotBlank
    private String doctorName;

    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    private String status;

    @NotBlank
    private String notes;

    @NotNull
    private Long version;

    @NotNull
    private OffsetDateTime sentAt;

    @PrePersist
    public void prePersist() {
        setSentAtAsNow();
    }

    @PreUpdate
    public void preUpdate() {
        setSentAtAsNow();
    }

    private void setSentAtAsNow() {
        this.sentAt = OffsetDateTime.now();
    }

}
