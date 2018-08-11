package pt.hospetall.web.controller;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.error.exceptions.InvalidPeriodException;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.util.CalendarPeriod;
import pt.hospetall.web.util.ShiftUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RepositoryRestController
public class AvailablePeriodController {

	@Autowired
	private INurseRepository nurseRepository;

	@Autowired
	private IVeterinarianRepository veterinarianRepository;

	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	@GetMapping(path = "/available/nurse", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetween(
			@RequestParam(name = "start") long start,
			@RequestParam(name = "end") long end
	) throws InvalidPeriodException {
		CalendarPeriod calendarPeriod = getCalendarPeriod(start, end);

		LinkedList<Nurse> nurses = new LinkedList<>();
		nurseRepository.findAll().forEach(nurses::add);

		List<NurseShift> shifts = nurses.stream()
				.flatMap(nurse -> {
					List<CalendarPeriod> available = getAllAvailablePeriodsNurseBetween(nurse, calendarPeriod);
					return available.stream().map(cp -> calendarPeriodToNurseShift(nurse, cp));
				})
				.collect(Collectors.toList());


		return ResponseEntity.ok().build();
	}

	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	@GetMapping(path = "/available/nurse/{id}", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetweenFor(
			@RequestParam(name = "start") long start,
			@RequestParam(name = "end") long end,
			@PathVariable(name="id") int id
	) throws InvalidPeriodException, PersonNotFoundException {
		CalendarPeriod calendarPeriod = getCalendarPeriod(start, end);

		Nurse nurse = nurseRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);

		List<CalendarPeriod> available = getAllAvailablePeriodsNurseBetween(nurse, calendarPeriod);
		List<NurseShift> shifts = available
				.stream()
				.map(cp -> calendarPeriodToNurseShift(nurse, cp))
				.collect(Collectors.toList());

		return ResponseEntity.ok().build();
	}

	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	@GetMapping(path = "/available/veterinarian", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetweenVeterinarian(
			@RequestParam(name = "start") long start,
			@RequestParam(name = "end") long end
	) throws InvalidPeriodException {
		CalendarPeriod calendarPeriod = getCalendarPeriod(start, end);

		LinkedList<Veterinarian> vets = new LinkedList<>();
		veterinarianRepository.findAll().forEach(vets::add);

		List<VeterinarianShift> shifts = vets
				.stream()
				.flatMap(vet -> {
					List<CalendarPeriod> available = getAllAvailablePeriodsVeterinarianBetween(vet, calendarPeriod);
					return available.stream().map(cp -> calendarPeriodToVeterinarianShift(vet, cp));
				})
				.collect(Collectors.toList());


		return ResponseEntity.ok().build();
	}

	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	@GetMapping(path = "/available/veterinarian/{id}", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetweenForVeterinarian(
			@RequestParam(name = "start") long start,
			@RequestParam(name = "end") long end,
			@PathVariable(name="id") int id
	) throws InvalidPeriodException, PersonNotFoundException {
		CalendarPeriod calendarPeriod = getCalendarPeriod(start, end);

		Veterinarian vet = veterinarianRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);


		List<CalendarPeriod> available = getAllAvailablePeriodsVeterinarianBetween(vet, calendarPeriod);
		List<VeterinarianShift> shifts = available
				.stream()
				.map(cp -> calendarPeriodToVeterinarianShift(vet, cp))
				.collect(Collectors.toList());

		return ResponseEntity.ok().build();
	}

	/**
	 * Gets calendar period
	 */
	private CalendarPeriod getCalendarPeriod(long start, long end) throws InvalidPeriodException{
		if(start > end)
			throw new InvalidPeriodException("Start parameter can't be bigger than end period.");

		Calendar start_calendar, end_calendar;
		start_calendar = end_calendar = Calendar.getInstance();
		if (start > 0)
			start_calendar.setTimeInMillis(start);
		if (end > 0)
			end_calendar.setTimeInMillis(end);

		return new CalendarPeriod(start, end);
	}

	/**
	 * Parses from CalendarPeriod instance to NurseShift instance, given a nurse
	 * @return new NurseShift
	 */
	private NurseShift calendarPeriodToNurseShift(Nurse nurse, CalendarPeriod cp){
		NurseShift ns = new NurseShift();
		ns.setNurse(nurse);
		ns.setWeekly(false);
		ns.setStart_period(cp.getStart().getTimeInMillis());
		ns.setEnd_period(cp.getEnd().getTimeInMillis());
		return ns;
	}

	/**
	 * Parses from CalendarPeriod instance to VeterinarianShift instance, given a veterinarian
	 * @return new VeterinarianShift
	 */
	private VeterinarianShift calendarPeriodToVeterinarianShift(Veterinarian veterinarian,
																CalendarPeriod cp){
		VeterinarianShift ns = new VeterinarianShift();
		ns.setVeterinarian(veterinarian);
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
	private List<CalendarPeriod> getAllAvailablePeriodsVeterinarianBetween(Veterinarian veterinarian,
																		   CalendarPeriod calendarPeriod){
		List<CalendarPeriod> allShifts = ShiftUtil.getAllVeterinarianShift(veterinarian, calendarPeriod);

		//Get all taken periods
		Stream<CalendarPeriod> scheduleStream = veterinarian.getSchedules()
				.stream()
				.map(CalendarPeriod::new)
				.filter(cp -> cp.inside(calendarPeriod));

		return getAvailablePeriodsBetween(allShifts, scheduleStream);
	}

	/**
	 * Get all available periods of a nurse
	 * @param calendarPeriod the period that marks the start and end of all available periods that we want
	 * @return list of available periods for a nurse
	 */
	private List<CalendarPeriod> getAllAvailablePeriodsNurseBetween(Nurse nurse,
																	CalendarPeriod calendarPeriod){
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
