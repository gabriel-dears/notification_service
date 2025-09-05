package com.hospital_app.notification_service.infra.db;


import com.hospital_app.common.db.DbOperationWrapper;
import com.hospital_app.notification_service.application.exception.AppointmentEmailDbException;

public class AppointmentEmailDbOperationWrapper {

    public static <T> T execute(DbOperationWrapper.DbOperation<T> operation) {
        return DbOperationWrapper.execute(operation, AppointmentEmailDbException.class);
    }

}
