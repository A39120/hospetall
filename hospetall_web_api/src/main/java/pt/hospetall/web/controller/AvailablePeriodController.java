package pt.hospetall.web.controller;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.util.CalendarPeriod;
import pt.hospetall.web.util.ShiftUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RepositoryRestController
public class AvailablePeriodController {

	@Autowired
	private INurseRepository nurseRepository;

	/**
	 * @param start
	 * @param end
	 * @return
	 */
	@GetMapping(path = "/available/nurse", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetween(
			@RequestParam(name = "start") long start,
			@RequestParam(name = "end") long end
	){
		LinkedList<Nurse> nurses = new LinkedList<>();
		nurseRepository.findAll().forEach(nurses::add);

		Calendar start_calendar, end_calendar;
		start_calendar = end_calendar = Calendar.getInstance();
		if(start > 0)
			start_calendar.setTimeInMillis(start);
		if(end > 0)
			end_calendar.setTimeInMillis(end);

		CalendarPeriod calendarPeriod = new CalendarPeriod(start, end);

		List<NurseShift> shifts = nurses.stream()
				.flatMap(nurse -> {
					List<CalendarPeriod> available = getAllAvailablePeriodsNurseBetween(nurse, calendarPeriod);
					return available.stream().map(cp -> calendarPeriodToNurseShift(nurse, cp));
				})
				.collect(Collectors.toList());


		return ResponseEntity.ok().build();
	}

	private NurseShift calendarPeriodToNurseShift(Nurse nurse, CalendarPeriod cp){
		NurseShift ns = new NurseShift();
		ns.setNurse(nurse);
		ns.setWeekly(false);
		ns.setStart_period(cp.getStart().getTimeInMillis());
		ns.setEnd_period(cp.getEnd().getTimeInMillis());
		return ns;
	}

	/**
	 * Get all available periods of a nurse
	 * @param calendarPeriod the period that marks the start and end of all available periods that we want
	 * @return list of available periods for a nurse
	 */
	private List<CalendarPeriod> getAllAvailablePeriodsNurseBetween(Nurse nurse, CalendarPeriod calendarPeriod){
		List<CalendarPeriod> allShifts = ShiftUtil.getAllNurseShifts(nurse, calendarPeriod);

		//Get all taken periods
		Stream<CalendarPeriod> scheduleStream = nurse.getSchedules()
				.stream()
				.map(CalendarPeriod::new)
				.filter(cp -> cp.inside(calendarPeriod));

		return getAvailablePeriodsBetween(allShifts, scheduleStream);
	}

	/**
	 * Util function to get all the available periods.
	 * @param allShifts - all periods
	 * @param scheduleStream - all taken periods
	 * @return list of available periods
	 */
	private List<CalendarPeriod> getAvailablePeriodsBetween(List<CalendarPeriod> allShifts,
															Stream<CalendarPeriod> scheduleStream){

		LinkedList<CalendarPeriod> available = new LinkedList<>();
		allShifts.forEach(shift -> {
			List<CalendarPeriod> calendars = scheduleStream
					.filter(s -> s.inside(shift))
					.sorted(Comparator.comparingLong(c -> c.getStart().getTimeInMillis()))
					.collect(Collectors.toList());

			if(calendars.isEmpty())
				available.add(shift);
			else{
				CalendarPeriod aux = shift;
				for (CalendarPeriod current: calendars) {
					Pair<CalendarPeriod, CalendarPeriod> split = aux.split(current);
					available.add(split.getKey());
					aux = split.getValue();
				}
				available.add(aux);
			}
		});
		return available;
	}


}
