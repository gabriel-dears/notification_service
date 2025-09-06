package com.hospital_app.notification_service.infra.adapter.out.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAppointmentEmailRepository extends JpaRepository<JpaAppointmentEmailEntity, UUID> {

    Optional<JpaAppointmentEmailEntity> findByAppointmentId(UUID appointmentId);

    void deleteByStatusIn(Collection<String> statuses);
}
