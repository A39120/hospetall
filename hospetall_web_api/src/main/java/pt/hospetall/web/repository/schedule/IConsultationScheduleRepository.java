package pt.hospetall.web.repository.schedule;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.repository.base.IScheduleRepository;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIAN', 'ROLE_RECEPTIONIST')")
@RepositoryRestResource(collectionResourceRel = "consultation_schedules", path = "consultation_schedule")
public interface IConsultationScheduleRepository extends IScheduleRepository<ConsultationSchedule> { }
