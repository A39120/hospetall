package pt.hospetall.web.repository;


import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.ConsultationSchedule;

@RepositoryRestResource(collectionResourceRel = "consultation_schedules", path = "consultation_schedule")
public interface IConsultationScheduleRepository extends IScheduleRepository<ConsultationSchedule> { }
