package pt.hospetall.web.repository;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.ConsultationSchedule;

import java.util.Optional;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIAN', 'ROLE_RECEPTIONIST')")
@RepositoryRestResource(collectionResourceRel = "consultation_schedules", path = "consultation_schedule")
public interface IConsultationScheduleRepository extends IScheduleRepository<ConsultationSchedule> { }
