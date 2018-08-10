package pt.hospetall.web.repository.schedule;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.repository.base.IScheduleRepository;

@RepositoryRestResource(collectionResourceRel = "treatment_schedules", path = "treatment_schedule")
public interface ITreatmentScheduleRepository extends IScheduleRepository<TreatmentSchedule> { }

