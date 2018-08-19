package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.Schedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.repository.base.IScheduleRepository;
import pt.hospetall.web.repository.schedule.IConsultationScheduleRepository;
import pt.hospetall.web.repository.schedule.ITreatmentScheduleRepository;

import java.util.PriorityQueue;

@Service
public class ScheduleService {

	@Autowired
	private ShiftService shiftService;

	private final IConsultationScheduleRepository consultationScheduleRepository;
	private final ITreatmentScheduleRepository treatmentScheduleRepository;

	@Autowired
	public ScheduleService(ITreatmentScheduleRepository treatmentScheduleRepository,
						   IConsultationScheduleRepository consultationScheduleRepository) {
		this.treatmentScheduleRepository = treatmentScheduleRepository;
		this.consultationScheduleRepository = consultationScheduleRepository;
	}

	private  <T extends Schedule, U extends IScheduleRepository<T>> PriorityQueue<T> getSchedules(U repo){
		return repo.findAll().stream().collect(PriorityQueue::new, PriorityQueue::add, PriorityQueue::addAll);
	}

	private <T extends Schedule, U extends IScheduleRepository<T>> PriorityQueue<T> getSchedulesBetween(U repo, long start, long end){
		return repo.findAllByStartPeriodAfter(start)
				.stream()
				.collect(PriorityQueue::new, (q, sch) -> {
					if(sch.calculateEnd() < end)
						q.add(sch);
					},
					PriorityQueue::addAll);
	}

	public PriorityQueue<ConsultationSchedule> getConsultationSchedulesBetween(long start, long end){
		return getSchedulesBetween(consultationScheduleRepository, start, end);
	}

	public PriorityQueue<TreatmentSchedule> getTreatmentSchedulesBetween(long start, long end){
		return getSchedulesBetween(treatmentScheduleRepository, start, end);
	}

	public PriorityQueue<ConsultationSchedule> getVeterinarianSchedulesBetween(Veterinarian veterinarian, long start, long end){
		return veterinarian.getSchedules()
				.stream()
				.collect(PriorityQueue::new, (q, sch) -> {
							if(sch.calculateEnd() < end && sch.getStartPeriod() > start)
								q.add(sch);
						},
						PriorityQueue::addAll);
	}

	public PriorityQueue<TreatmentSchedule> getNurseSchedulesBetween(Nurse nurse, long start, long end){
		return nurse.getSchedules()
				.stream()
				.collect(PriorityQueue::new, (q, sch) -> {
							if(sch.calculateEnd()  < end && sch.getStartPeriod() > start)
								q.add(sch);
						},
						PriorityQueue::addAll);
	}

}
