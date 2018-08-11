package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.error.exceptions.ScheduleConflictException;
import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.Schedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.Shift;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.schedule.IConsultationScheduleRepository;
import pt.hospetall.web.repository.schedule.ITreatmentScheduleRepository;
import pt.hospetall.web.services.ShiftService;
import pt.hospetall.web.util.CalendarPeriod;
import pt.hospetall.web.util.CalendarUtil;

import java.net.URI;
import java.util.List;

@RepositoryRestController
@RequestMapping
public class ScheduleController {

	private final IVeterinarianRepository veterinarianRepository;
	private final ShiftService shiftService;
	private final INurseRepository nurseRepository;
	private final IClientRepository clientRepository;
	private final ITreatmentScheduleRepository treatmentScheduleRepository;
	private final IConsultationScheduleRepository consultationScheduleRepository;

	@Autowired
	public ScheduleController(IVeterinarianRepository veterinarianRepository,
							  ShiftService shiftService,
							  INurseRepository nurseRepository,
							  IClientRepository clientRepository,
							  ITreatmentScheduleRepository treatmentScheduleRepository,
							  IConsultationScheduleRepository consultationScheduleRepository) {

		this.veterinarianRepository = veterinarianRepository;
		this.shiftService = shiftService;
		this.nurseRepository = nurseRepository;
		this.clientRepository = clientRepository;
		this.treatmentScheduleRepository = treatmentScheduleRepository;
		this.consultationScheduleRepository = consultationScheduleRepository;
	}


	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(path = "/schedule/treatment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity clientScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="nurse", required = false) int nurse_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();

		Client client = clientRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		Nurse nurse = nurseRepository.findById(nurse_id)
				.orElseThrow(() -> new PersonNotFoundException("Nurse not found."));

		return createTreatmentSchedule(treatmentSchedule, client, nurse);
	}

	private <U extends Schedule> boolean isScheduleValid(List<? extends Shift> available, U schedule){
		CalendarPeriod period = new CalendarPeriod(schedule);
		return available.stream()
				.map(CalendarPeriod::new)
				.anyMatch(period::inside);
	}

	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(path = "/schedule/consultation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity clientScheduleConsultation(
			@RequestBody ConsultationSchedule consultationSchedule,
			@RequestParam(name="veterinarian", required = false) int vet_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();

		Client client = clientRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		Veterinarian veterinarian = veterinarianRepository.findById(vet_id)
				.orElseThrow(() -> new PersonNotFoundException("Nurse not found."));

		return createConsultationSchedule(consultationSchedule, client, veterinarian);
	}

	@PreAuthorize("hasRole('ROLE_VETERINARIAN')")
	@PostMapping(path = "/schedule/consultation/veterinarian", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity veterinarianScheduleConsultation(
			@RequestBody ConsultationSchedule consultationSchedule,
			@RequestParam(name="client", required = false) int client_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		Veterinarian veterinarian = veterinarianRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Veterinarian not found."));

		return createConsultationSchedule(consultationSchedule, client, veterinarian);
	}

	@PreAuthorize("hasRole('ROLE_RECEPTIONIST')")
	@PostMapping(path = "/schedule/consultation/receptionist", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity receptionistScheduleConsultation(
			@RequestBody ConsultationSchedule consultationSchedule,
			@RequestParam(name="client") int client_id,
			@RequestParam(name="veterinarian") int vet_id ) throws PersonNotFoundException, ScheduleConflictException {

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		Veterinarian veterinarian = veterinarianRepository.findById(vet_id)
				.orElseThrow(() -> new PersonNotFoundException("Veterinarian not found."));

		return createConsultationSchedule(consultationSchedule, client, veterinarian);
	}

	@PreAuthorize("hasAnyRole('ROLE_NURSE')")
	@PostMapping(path = "/schedule/treatment/nurse", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity nurseScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="client", required = false) int client_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();
		Nurse nurse = nurseRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Nurse not found."));

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		return createTreatmentSchedule(treatmentSchedule, client, nurse);
	}

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST')")
	@PostMapping(path = "/schedule/treatment/receptionist", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity receptionistScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="client", required = false) int client_id,
			@RequestParam(name="nurse", required = false) int nurse_id ) throws PersonNotFoundException, ScheduleConflictException {

		Nurse nurse = nurseRepository.findById(nurse_id)
				.orElseThrow(() -> new PersonNotFoundException("Nurse not found."));

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		return createTreatmentSchedule(treatmentSchedule, client, nurse);
	}

	private ResponseEntity createTreatmentSchedule(TreatmentSchedule treatmentSchedule,
												   Client client,
												   Nurse nurse) throws ScheduleConflictException{
		CalendarPeriod calendarPeriod = new CalendarPeriod(treatmentSchedule);
		calendarPeriod = new CalendarPeriod(
				CalendarUtil.getEndOfDayFromCalendar(calendarPeriod.getStart()),
				CalendarUtil.getEndOfDayFromCalendar(calendarPeriod.getEnd()));

		if(!isScheduleValid(
				shiftService.getAllAvailableBetweenFor(calendarPeriod.getStart().getTimeInMillis(),
						calendarPeriod.getEnd().getTimeInMillis(),
						nurse.getId()),
				(Schedule)nurse.getSchedules()))
			throw new ScheduleConflictException();

		treatmentSchedule.setNurse(nurse);
		treatmentSchedule.setClient(client);
		treatmentSchedule = treatmentScheduleRepository.save(treatmentSchedule);

		return ResponseEntity.created(URI.create("/treatmentschedule/" + treatmentSchedule.getId())).build();
	}

	private ResponseEntity createConsultationSchedule(ConsultationSchedule consultationSchedule,
													  Client client,
													  Veterinarian veterinarian) throws ScheduleConflictException{

		CalendarPeriod calendarPeriod = new CalendarPeriod(consultationSchedule);
		calendarPeriod = new CalendarPeriod(
				CalendarUtil.getEndOfDayFromCalendar(calendarPeriod.getStart()),
				CalendarUtil.getEndOfDayFromCalendar(calendarPeriod.getEnd()));

		if(!isScheduleValid(
				shiftService.getAllAvailableBetweenForVeterinarian(calendarPeriod.getStart().getTimeInMillis(),
						calendarPeriod.getEnd().getTimeInMillis(),
						veterinarian.getId()),
				(Schedule)veterinarian.getSchedules()))
			throw new ScheduleConflictException();

		consultationSchedule.setVeterinarian(veterinarian);
		consultationSchedule.setClient(client);
		consultationSchedule = consultationScheduleRepository.save(consultationSchedule);

		return ResponseEntity.created(URI.create("/consultationschedule/" + consultationSchedule.getId())).build();
	}
}
