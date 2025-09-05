package com.hospital_app.notification_service.infra.db;

import com.hospital_app.notification_service.application.exception.AppointmentEmailDbException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentEmailDbOperationWrapperTest {

    @Test
    void execute_returnsValue_whenNoException() {
        String result = AppointmentEmailDbOperationWrapper.execute(() -> "ok");
        assertEquals("ok", result);
    }

    @Test
    void execute_wrapsExceptionIntoAppointmentEmailDbException() {
        AppointmentEmailDbException ex = assertThrows(
                AppointmentEmailDbException.class,
                () -> AppointmentEmailDbOperationWrapper.execute(() -> { throw new AppointmentEmailDbException("boom"); })
        );
        assertNotNull(ex.getMessage());
    }
}
