package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.application.port.in.RemoveObsoleteAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;

public class RemoveObsoleteItemsService implements RemoveObsoleteAppointmentEmailUseCase {

    private final CustomAppointmentEmailRepository customAppointmentEmailRepository;

    public RemoveObsoleteItemsService(CustomAppointmentEmailRepository customAppointmentEmailRepository) {
        this.customAppointmentEmailRepository = customAppointmentEmailRepository;
    }

    @Override
    public void execute() {
        customAppointmentEmailRepository.deleteObsolete();
    }
}
