package pt.hospetall.web.util;

import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.Shift;
import pt.hospetall.web.model.shift.VeterinarianShift;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShiftUtil {

	public static List<CalendarPeriod> getAllNurseShifts(Nurse nurse, CalendarPeriod calendarPeriod){
		Set<NurseShift> shifts = nurse.getShifts();
		Set<TreatmentSchedule> schedules = nurse.getSchedules();

		//Get periodic shifts
		Stream<CalendarPeriod> shiftWeeklyPeriod = shifts
				.stream()
				.filter(Shift::isWeekly)
				.map(CalendarPeriod::new);

		final Calendar[] current = {calendarPeriod.getStart()};

		LinkedList<CalendarPeriod> allShifts = getWeeklyShift(calendarPeriod, shiftWeeklyPeriod, current);

		//Get non periodic shifts
		List<CalendarPeriod> shiftNotWeeklyPeriod = shifts.stream()
				.filter(s -> !s.isWeekly())
				.map(CalendarPeriod::new)
				.filter(cp -> cp.inside(calendarPeriod))
				.collect(Collectors.toList());

		allShifts.addAll(shiftNotWeeklyPeriod);
		return allShifts;
	}

	public static List<CalendarPeriod> getAllVeterinarianShift(Veterinarian veterinarian, CalendarPeriod calendarPeriod){
		Set<VeterinarianShift> shifts = veterinarian.getShifts();
		Set<ConsultationSchedule> schedules = veterinarian.getSchedules();

		//Get periodic shifts
		Stream<CalendarPeriod> shiftWeeklyPeriod = shifts
				.stream()
				.filter(Shift::isWeekly)
				.map(CalendarPeriod::new);

		final Calendar[] current = {calendarPeriod.getStart()};

		LinkedList<CalendarPeriod> allShifts = getWeeklyShift(calendarPeriod, shiftWeeklyPeriod, current);

		//Get non periodic shifts
		List<CalendarPeriod> shiftNotWeeklyPeriod = shifts.stream()
				.filter(s -> !s.isWeekly())
				.map(CalendarPeriod::new)
				.filter(cp -> cp.inside(calendarPeriod))
				.collect(Collectors.toList());

		allShifts.addAll(shiftNotWeeklyPeriod);
		return allShifts;
	}

	private static LinkedList<CalendarPeriod> getWeeklyShift(CalendarPeriod calendarPeriod, Stream<CalendarPeriod> shiftWeeklyPeriod, Calendar[] current) {
		LinkedList<CalendarPeriod> allShifts = new LinkedList<>();
		while(current[0].before(calendarPeriod.getEnd())){
			final int currentDayOfTheWeek = current[0].get(Calendar.DAY_OF_WEEK);
			List<CalendarPeriod> aux = shiftWeeklyPeriod
					.filter(s -> s.getStart().get(Calendar.DAY_OF_WEEK) == currentDayOfTheWeek)
					.map(s -> {
						Calendar startAux = CalendarUtil.setDay(current[0], s.getStart());
						Calendar endAux = CalendarUtil.setDay(current[0], s.getEnd());
						while(endAux.before(startAux))
							endAux.add(Calendar.DAY_OF_YEAR, 1);

						return new CalendarPeriod(startAux, endAux);
					})
					.collect(Collectors.toList());

			allShifts.addAll(aux);
			current[0].add(Calendar.DAY_OF_YEAR, 1);
			current[0] = CalendarUtil.getStartOfDayFromCalendar(current[0]);
		}
		return allShifts;
	}
}
