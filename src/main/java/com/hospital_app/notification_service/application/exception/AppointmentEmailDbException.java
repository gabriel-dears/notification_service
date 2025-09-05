package com.hospital_app.notification_service.application.exception;

public class AppointmentEmailDbException extends RuntimeException {
    public AppointmentEmailDbException(String message) {
        super(message);
    }
}
