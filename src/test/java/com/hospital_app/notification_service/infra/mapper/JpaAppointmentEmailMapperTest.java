package com.hospital_app.notification_service.infra.mapper;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import com.hospital_app.notification_service.infra.adapter.out.db.jpa.JpaAppointmentEmailEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JpaAppointmentEmailMapperTest {

    private final JpaAppointmentEmailMapper mapper = new JpaAppointmentEmailMapper();

    @Test
    void toEntity_and_back_preservesFields() {
        AppointmentEmail domain = new AppointmentEmail();
        domain.setId(UUID.randomUUID());
        domain.setAppointmentId(UUID.randomUUID());
        domain.setPatientId(UUID.randomUUID());
        domain.setPatientEmail("p@mail.com");
        domain.setPatientName("Patient");
        domain.setDoctorId(UUID.randomUUID());
        domain.setDoctorName("Doctor");
        domain.setDateTime(LocalDateTime.of(2025, 4, 4, 16, 45));
        domain.setStatus("SCHEDULED");
        domain.setNotes("notes");
        domain.setVersion(5L);

        JpaAppointmentEmailEntity entity = mapper.toEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getAppointmentId(), entity.getAppointmentId());
        assertEquals(domain.getPatientId(), entity.getPatientId());
        assertEquals(domain.getPatientEmail(), entity.getPatientEmail());
        assertEquals(domain.getPatientName(), entity.getPatientName());
        assertEquals(domain.getDoctorId(), entity.getDoctorId());
        assertEquals(domain.getDoctorName(), entity.getDoctorName());
        assertEquals(domain.getDateTime(), entity.getDateTime());
        assertEquals(domain.getStatus(), entity.getStatus());
        assertEquals(domain.getNotes(), entity.getNotes());
        assertEquals(domain.getVersion(), entity.getVersion());

        AppointmentEmail back = mapper.toDomain(entity);
        assertNotNull(back);
        assertEquals(domain.getId(), back.getId());
        assertEquals(domain.getAppointmentId(), back.getAppointmentId());
        assertEquals(domain.getPatientId(), back.getPatientId());
        assertEquals(domain.getPatientEmail(), back.getPatientEmail());
        assertEquals(domain.getPatientName(), back.getPatientName());
        assertEquals(domain.getDoctorId(), back.getDoctorId());
        assertEquals(domain.getDoctorName(), back.getDoctorName());
        assertEquals(domain.getDateTime(), back.getDateTime());
        assertEquals(domain.getStatus(), back.getStatus());
        assertEquals(domain.getNotes(), back.getNotes());
        assertEquals(domain.getVersion(), back.getVersion());
    }

    @Test
    void nullHandling_returnsNull() {
        assertNull(mapper.toEntity(null));
        assertNull(mapper.toDomain(null));
    }
}
