package com.hospital_app.notification_service.infra.adapter.out.db.jpa;

import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import com.hospital_app.notification_service.infra.db.AppointmentEmailDbOperationWrapper;
import com.hospital_app.notification_service.infra.mapper.JpaAppointmentEmailMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaCustomAppointmentEmailRepository implements CustomAppointmentEmailRepository {

    private final JpaAppointmentEmailRepository repository;
    private final JpaAppointmentEmailMapper jpaAppointmentEmailMapper;

    public JpaCustomAppointmentEmailRepository(JpaAppointmentEmailRepository repository, JpaAppointmentEmailMapper jpaAppointmentEmailMapper) {
        this.repository = repository;
        this.jpaAppointmentEmailMapper = jpaAppointmentEmailMapper;
    }

    @Override
    public Optional<AppointmentEmail> findByAppointmentId(UUID id) {
        Optional<JpaAppointmentEmailEntity> optEntity = AppointmentEmailDbOperationWrapper.execute(() -> repository.findByAppointmentId((id)));
        return optEntity.map(jpaAppointmentEmailMapper::toDomain);

    }

    @Override
    public AppointmentEmail save(AppointmentEmail appointmentEmail) {
        JpaAppointmentEmailEntity entity = jpaAppointmentEmailMapper.toEntity(appointmentEmail);
        JpaAppointmentEmailEntity savedEntity = AppointmentEmailDbOperationWrapper.execute(() -> repository.save(entity));
        return jpaAppointmentEmailMapper.toDomain(savedEntity);
    }

}
