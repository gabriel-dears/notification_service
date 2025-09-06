package com.hospital_app.notification_service.infra.adapter.in;

import com.hospital_app.notification_service.application.port.in.RemoveObsoleteAppointmentEmailUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveObsoleteAppointmentEmailJob {

    private final RemoveObsoleteAppointmentEmailUseCase removeObsoleteAppointmentEmailUseCase;

    public RemoveObsoleteAppointmentEmailJob(RemoveObsoleteAppointmentEmailUseCase removeObsoleteAppointmentEmailUseCase) {
        this.removeObsoleteAppointmentEmailUseCase = removeObsoleteAppointmentEmailUseCase;
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void run() {
        removeObsoleteAppointmentEmailUseCase.execute();
    }

}
