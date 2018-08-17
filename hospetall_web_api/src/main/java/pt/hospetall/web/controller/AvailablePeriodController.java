package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.INurseShiftRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.services.AvailableHoursService;
import pt.hospetall.web.services.ScheduleService;
import pt.hospetall.web.services.ShiftService;
import pt.hospetall.web.util.PageableImpl;

import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@RequestMapping(path = "/available")
public class AvailablePeriodController {

	@Autowired
	private PagedResourcesAssembler<NurseShift> nurseShiftPagedResourcesAssembler;

	@Autowired
	private PagedResourcesAssembler<VeterinarianShift> veterinarianShiftPagedResourcesAssembler;

	@Autowired
	private AvailableHoursService availableHoursService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private INurseRepository nurseRepository;

	@Autowired
	private IVeterinarianRepository veterinarianRepository;

	@GetMapping(path = "/nurse")
	public ResponseEntity<PagedResources<Resource<NurseShift>>> getAllNurseShiftsAvailableBetween(
		@RequestParam(name = "start", defaultValue = "0") long start,
		@RequestParam(name = "end", defaultValue = "0") long end,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "20", required = false) int size
	){
		final long fStart = getStart(start),
				fEnd = getEnd(end);

		List<Nurse> nurses = nurseRepository.findAll();
		PriorityQueue<NurseShift> nurseShifts = nurses.stream()
				.flatMap(nurse -> {
					PriorityQueue<NurseShift> shf = shiftService.getNurseShiftBetween(nurse, fStart, fEnd);
					PriorityQueue<TreatmentSchedule> sch = scheduleService.getNurseSchedulesBetween(nurse, fStart, fEnd);

					return availableHoursService.nurseSplitIntoAvailableHours(shf, sch).stream();
				})
				.collect(PriorityQueue::new, (queue, el) -> { if(el!= null) queue.add(el); }, PriorityQueue::addAll);

		Link link = linkTo(methodOn(AvailablePeriodController.class).getAllNurseShiftsAvailableBetween(start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedNurseShift(nurseShifts, link, page, size));

	}

	private long getEnd(long end){
		if(end == 0){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			return calendar.getTimeInMillis();
		}

		return end;
	}

	private long getStart(long start){
		if(start == 0){
			Calendar calendar = Calendar.getInstance();
			return calendar.getTimeInMillis();
		}

		return start;
	}



	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	/*@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/nurse", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity<PagedResources<Resource<NurseShift>>> getAllAvailableBetween(
			@RequestParam(name = "start", defaultValue = "0") long start,
			@RequestParam(name = "end", defaultValue = "0") long end,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "20", required = false) int size
	) throws InvalidPeriodException {
		CalendarPeriod calendarPeriod = getCalendarPeriod(start, end);

		LinkedList<Nurse> nurses = new LinkedList<>();
		nurseRepository.findAll().forEach(nurses::add);

		List<NurseShift> shifts = nurses.stream()
				.flatMap(nurse -> {
					List<CalendarPeriod> available = getAllAvailablePeriodsNurseBetween(nurse, calendarPeriod);
					return available.stream().map(cp -> calendarPeriodToNurseShift(nurse, cp));
				})
				.sorted((ns1, ns2) -> (int)(ns1.getStartPeriod() - ns2.getStartPeriod()))
				.collect(Collectors.toList());

		Link link = linkTo(methodOn(AvailablePeriodController.class).getAllAvailableBetween(start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedNurseShift(shifts, link, page, size));
	}

	/**
	 * Returns all the shifts from all the nurses that have slots available.
	 * @param start the start of the period that of the shift we want is in, all shifts after this will be ignored
	 * @param end the end of the period of the shifts we want is in, all shifts after this will be ignored
	 * @return 200 if everything is ok
	 */
	/**@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/nurse/{id}", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getAllAvailableBetweenFor(
			@RequestParam(name = "start", defaultValue = "0") long start,
			@RequestParam(name = "end", defaultValue = "0") long end,
			@PathVariable(name="id") int id,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "20", required = false) int size
	) throws InvalidPeriodException, PersonNotFoundException {

		Nurse nurse = nurseRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);

		List<CalendarPeriod> available =
				getAllAvailablePeriodsNurseBetween(nurse, calendarPeriod);

		List<NurseShift> shifts = available
				.stream()
				.map(cp -> calendarPeriodToNurseShift(nurse, cp))
				.collect(Collectors.toList());

		Link link = linkTo(methodOn(AvailablePeriodController.class).getAllAvailableBetween(start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedNurseShift(shifts, link, page, size));
	}
	*/

	private PagedResources<Resource<NurseShift>> getPagedNurseShift(PriorityQueue<NurseShift> shifts,
																	Link link,
																	int page,
																	int size){
		int listSize = shifts.size();
		int currentOffset = page * size;
		List<NurseShift> paged = shifts
				.stream()
				.skip(currentOffset)
				.limit(size)
				.collect(Collectors.toList());

		Pageable pageable;
		if(paged.size() > 0)
			pageable = new PageableImpl(listSize, size, page);
		else
			pageable = Pageable.unpaged();

		return nurseShiftPagedResourcesAssembler.toResource( new PageImpl(paged, pageable, listSize), link );
	}







}
