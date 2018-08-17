package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.Schedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.Shift;
import pt.hospetall.web.model.shift.VeterinarianShift;

import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Service
public class AvailableHoursService {

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private ScheduleService scheduleService;

	private <T extends Shift, U extends Schedule> PriorityQueue<T> splitIntoAvailableHours(PriorityQueue<T> shifts,
																						  PriorityQueue<U> schedules,
																						  BiFunction<T, U, PriorityQueue<T>> splitter){

		PriorityQueue<T> available = new PriorityQueue<>();

		Supplier<T> extractShift = () -> shifts.isEmpty() ? null : shifts.remove();
		Supplier<U> extractSchedule = () -> schedules.isEmpty() ? null : schedules.remove();

		U currentSchedule = extractSchedule.get();
		T currentShift = extractShift.get();

		while(currentSchedule != null && currentShift != null) {

			if(!currentSchedule.inside(currentShift))
				available.add(currentShift);
			else {
				PriorityQueue<T> split = splitter.apply(currentShift, currentSchedule);
				available.addAll(splitIntoAvailableHours(split, schedules, splitter));
			}

			currentShift = extractShift.get();
		}

		if(currentSchedule == null && currentShift != null){
			available.add(currentShift);
			available.addAll(shifts);
		}

		return available;
	}

	public PriorityQueue<NurseShift> nurseSplitIntoAvailableHours(PriorityQueue<NurseShift> shifts,
																  PriorityQueue<TreatmentSchedule> schedules){
		return splitIntoAvailableHours(shifts, schedules, this::nurseShiftSplit);
	}

	public PriorityQueue<VeterinarianShift> veterinarianSplitIntoAvailableHours(PriorityQueue<VeterinarianShift> shifts,
																				PriorityQueue<ConsultationSchedule> schedules){
		return splitIntoAvailableHours(shifts, schedules, this::veterinarianShiftSplit);
	}

	private PriorityQueue<NurseShift> nurseShiftSplit(NurseShift shift, TreatmentSchedule treatmentSchedule){
		NurseShift first = new NurseShift();
		NurseShift second = new NurseShift();

		first.setStartPeriod(shift.getStartPeriod());
		int minutes = (int)((treatmentSchedule.getStartPeriod() - shift.getStartPeriod())/(60 * 1000)) + 1;
		first.setMinutes(minutes);
		second.setStartPeriod(treatmentSchedule.calculateEnd());
		minutes = (int)((shift.calculateEnd() - second.getStartPeriod())/(60 * 1000)) + 1;
		second.setMinutes(minutes);
		PriorityQueue<NurseShift> shifts = new PriorityQueue<>();

		shifts.add(first);
		shifts.add(second);
		return shifts;
	}

	private PriorityQueue<VeterinarianShift> veterinarianShiftSplit(VeterinarianShift shift, ConsultationSchedule schedule){
		VeterinarianShift first = new VeterinarianShift();
		VeterinarianShift second = new VeterinarianShift();

		first.setStartPeriod(shift.getStartPeriod());
		int minutes = (int)(schedule.getStartPeriod() - shift.getStartPeriod());
		first.setMinutes(minutes);
		second.setStartPeriod(schedule.calculateEnd());
		minutes = (int)(shift.calculateEnd() - second.getStartPeriod());
		second.setMinutes(minutes);
		PriorityQueue<VeterinarianShift> shifts = new PriorityQueue<>();

		shifts.add(first);
		shifts.add(second);
		return shifts;
	}
}
