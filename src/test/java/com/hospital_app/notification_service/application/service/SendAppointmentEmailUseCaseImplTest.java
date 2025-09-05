package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.application.port.out.email.AppointmentEmailForUpdateSender;
import com.hospital_app.notification_service.application.port.out.email.NewAppointmentEmailSender;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class SendAppointmentEmailUseCaseImplTest {

    private CustomAppointmentEmailRepository repository;
    private NewAppointmentEmailSender newSender;
    private AppointmentEmailForUpdateSender updateSender;

    private SendAppointmentEmailUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(CustomAppointmentEmailRepository.class);
        newSender = mock(NewAppointmentEmailSender.class);
        updateSender = mock(AppointmentEmailForUpdateSender.class);
        useCase = new SendAppointmentEmailUseCaseImpl(repository, newSender, updateSender);
    }

    @Test
    void execute_whenNoExistingAppointment_sendsNewAndSaves() {
        AppointmentEmail incoming = buildEmail();
        when(repository.findByAppointmentId(incoming.getAppointmentId())).thenReturn(Optional.empty());

        useCase.execute(incoming);

        verify(newSender).sendAppointmentEmail(incoming);
        verify(updateSender, never()).sendAppointmentEmail(any(), any());
        verify(repository).save(incoming);
    }

    @Test
    void execute_whenExistingAppointment_updatesSendsUpdateAndSavesExisting() {
        AppointmentEmail incoming = new AppointmentEmail();
        UUID appointmentId = UUID.randomUUID();
        incoming.setAppointmentId(appointmentId);
        incoming.setPatientName("Updated Name");
        incoming.setStatus("RESCHEDULED");
        incoming.setDateTime(LocalDateTime.of(2025, 3, 3, 14, 0));

        AppointmentEmail existing = new AppointmentEmail();
        existing.setId(UUID.randomUUID());
        existing.setAppointmentId(appointmentId);
        existing.setPatientName("Old Name");
        existing.setStatus("SCHEDULED");

        when(repository.findByAppointmentId(appointmentId)).thenReturn(Optional.of(existing));

        useCase.execute(incoming);

        // verify update sender called with existing and incoming
        verify(updateSender).sendAppointmentEmail(existing, incoming);
        // new sender should not be called
        verify(newSender, never()).sendAppointmentEmail(any());

        // ensure save called with the modified existing object
        ArgumentCaptor<AppointmentEmail> savedCaptor = ArgumentCaptor.forClass(AppointmentEmail.class);
        verify(repository).save(savedCaptor.capture());
        AppointmentEmail saved = savedCaptor.getValue();
        assertSame(existing, saved, "Should save the same existing instance after mapping");
        assertEquals("Updated Name", saved.getPatientName());
        assertEquals("RESCHEDULED", saved.getStatus());
        assertEquals(incoming.getDateTime(), saved.getDateTime());
    }

    private AppointmentEmail buildEmail() {
        AppointmentEmail e = new AppointmentEmail();
        e.setAppointmentId(UUID.randomUUID());
        e.setPatientId(UUID.randomUUID());
        e.setPatientEmail("john.doe@mail.com");
        e.setPatientName("John Doe");
        e.setDoctorId(UUID.randomUUID());
        e.setDoctorName("Dr. Smith");
        e.setDateTime(LocalDateTime.of(2025, 1, 1, 9, 0));
        e.setStatus("SCHEDULED");
        e.setNotes("N/A");
        e.setVersion(1L);
        return e;
    }
}
