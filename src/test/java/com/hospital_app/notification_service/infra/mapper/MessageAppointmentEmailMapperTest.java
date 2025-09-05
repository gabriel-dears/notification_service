package com.hospital_app.notification_service.infra.mapper;

import com.hospital_app.common.message.dto.AppointmentMessage;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MessageAppointmentEmailMapperTest {

    private final MessageAppointmentEmailMapper mapper = new MessageAppointmentEmailMapper();

    @Test
    void toEmail_mapsAllFields() {
        AppointmentMessage msg = new AppointmentMessage();
        UUID id = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        msg.setId(id);
        msg.setPatientId(patientId);
        msg.setDoctorId(doctorId);
        msg.setPatientName("Alice");
        msg.setPatientEmail("alice@mail.com");
        msg.setDoctorName("Dr. Bob");
        LocalDateTime dt = LocalDateTime.of(2025, 6, 6, 11, 15);
        msg.setDateTime(dt);
        msg.setStatus("SCHEDULED");
        msg.setNotes("Bring docs");
        msg.setVersion(3L);

        AppointmentEmail email = mapper.toEmail(msg);
        assertNotNull(email);
        assertEquals(id, email.getAppointmentId());
        assertEquals(patientId, email.getPatientId());
        assertEquals(doctorId, email.getDoctorId());
        assertEquals("Alice", email.getPatientName());
        assertEquals("alice@mail.com", email.getPatientEmail());
        assertEquals("Dr. Bob", email.getDoctorName());
        assertEquals(dt, email.getDateTime());
        assertEquals("SCHEDULED", email.getStatus());
        assertEquals("Bring docs", email.getNotes());
        assertEquals(3L, email.getVersion());
    }

    @Test
    void nullHandling_returnsNull() {
        assertNull(mapper.toEmail(null));
    }
}
