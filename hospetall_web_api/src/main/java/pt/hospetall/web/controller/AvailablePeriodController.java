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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.Shift;
import pt.hospetall.web.model.shift.VeterinarianShift;
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

		Link link = linkTo(methodOn(AvailablePeriodController.class)
				.getAllNurseShiftsAvailableBetween(start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedNurseShift(nurseShifts, link, page, size));
	}

	@GetMapping(path = "/veterinarian")
	public ResponseEntity<PagedResources<Resource<VeterinarianShift>>> getAllVeterinarianShiftsAvailableBetween(
			@RequestParam(name = "start", defaultValue = "0") long start,
			@RequestParam(name = "end", defaultValue = "0") long end,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "20", required = false) int size
	){
		final long fStart = getStart(start),
				fEnd = getEnd(end);

		List<Veterinarian> vets = veterinarianRepository.findAll();
		PriorityQueue<VeterinarianShift> veterinarianShifts = vets.stream()
				.flatMap(vet -> {
					PriorityQueue<VeterinarianShift> shf = shiftService.getVeterinarianShiftBetween(vet, fStart, fEnd);
					PriorityQueue<ConsultationSchedule> sch = scheduleService.getVeterinarianSchedulesBetween(vet, fStart, fEnd);
					return availableHoursService.veterinarianSplitIntoAvailableHours(shf, sch).stream();
				})
				.collect(PriorityQueue::new, (queue, el) -> { if(el!= null) queue.add(el); }, PriorityQueue::addAll);

		Link link = linkTo(methodOn(AvailablePeriodController.class)
				.getAllVeterinarianShiftsAvailableBetween(start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedVeterinarianShift(veterinarianShifts, link, page, size));
	}

	@GetMapping(path = "/nurse/{id}")
	public ResponseEntity<PagedResources<Resource<NurseShift>>> getNurseShiftsAvailableBetween(
			@PathVariable(name = "id") int nurse_id,
			@RequestParam(name = "start", defaultValue = "0") long start,
			@RequestParam(name = "end", defaultValue = "0") long end,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "20", required = false) int size
	){
		final long fStart = getStart(start),
				fEnd = getEnd(end);

		Nurse nurse = nurseRepository.findById(nurse_id).orElseThrow(PersonNotFoundException::new);
		PriorityQueue<NurseShift> shf = shiftService.getNurseShiftBetween(nurse, fStart, fEnd);
		PriorityQueue<TreatmentSchedule> sch = scheduleService.getNurseSchedulesBetween(nurse, fStart, fEnd);
		PriorityQueue<NurseShift> available =  availableHoursService.nurseSplitIntoAvailableHours(shf, sch);

		Link link = linkTo(methodOn(AvailablePeriodController.class)
				.getNurseShiftsAvailableBetween(nurse_id, start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedNurseShift(available, link, page, size));
	}

	@GetMapping(path = "/veterinarian/{id}")
	public ResponseEntity<PagedResources<Resource<VeterinarianShift>>> getVeterinarianShiftBetween(
			@PathVariable(name = "id") int vet_id,
			@RequestParam(name = "start", defaultValue = "0") long start,
			@RequestParam(name = "end", defaultValue = "0") long end,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "20", required = false) int size
	){
		final long fStart = getStart(start), fEnd = getEnd(end);

		Veterinarian veterinarian = veterinarianRepository.findById(vet_id).orElseThrow(PersonNotFoundException::new);
		PriorityQueue<VeterinarianShift> shf = shiftService.getVeterinarianShiftBetween(veterinarian, fStart, fEnd);
		PriorityQueue<ConsultationSchedule> sch = scheduleService.getVeterinarianSchedulesBetween(veterinarian, fStart, fEnd);
		PriorityQueue<VeterinarianShift> available =  availableHoursService.veterinarianSplitIntoAvailableHours(shf, sch);

		Link link = linkTo(methodOn(AvailablePeriodController.class)
				.getVeterinarianShiftBetween(vet_id, start, end, page, size))
				.withSelfRel();

		return ResponseEntity.ok(getPagedVeterinarianShift(available, link, page, size));
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

	private PagedResources<Resource<VeterinarianShift>> getPagedVeterinarianShift(PriorityQueue<VeterinarianShift> shifts,
																	Link link,
																	int page,
																	int size){
		return getPagedShift(shifts, link, page, size, veterinarianShiftPagedResourcesAssembler);
	}

	private PagedResources<Resource<NurseShift>> getPagedNurseShift(PriorityQueue<NurseShift> shifts,
																	Link link,
																	int page,
																	int size){
		return getPagedShift(shifts, link, page, size, nurseShiftPagedResourcesAssembler);
	}

	private <T extends Shift> PagedResources<Resource<T>> getPagedShift(PriorityQueue<T> shift,
																		Link link,
																		int page,
																		int size,
																		PagedResourcesAssembler<T> assembler){
		int listSize = shift.size();
		int currentOffset = page * size;
		List<T> paged = shift
				.stream()
				.skip(currentOffset)
				.limit(size)
				.collect(Collectors.toList());

		Pageable pageable;
		if(paged.size() > 0)
			pageable = new PageableImpl(listSize, size, page);
		else
			pageable = Pageable.unpaged();

		return assembler.toResource(new PageImpl<>(paged, pageable, listSize), link);
	}

}
