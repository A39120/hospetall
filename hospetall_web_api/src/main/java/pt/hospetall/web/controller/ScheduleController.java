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
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.schedule.IConsultationScheduleRepository;
import pt.hospetall.web.repository.schedule.ITreatmentScheduleRepository;
import pt.hospetall.web.services.AvailableHoursService;
import pt.hospetall.web.util.CalendarUtil;

import java.net.URI;
import java.util.Calendar;

@RepositoryRestController
@RequestMapping
public class ScheduleController {

	private final IVeterinarianRepository veterinarianRepository;
	private final INurseRepository nurseRepository;
	private final IClientRepository clientRepository;
	private final ITreatmentScheduleRepository treatmentScheduleRepository;
	private final IConsultationScheduleRepository consultationScheduleRepository;
	private final AvailableHoursService availableHoursService;

	@Autowired
	public ScheduleController(IVeterinarianRepository veterinarianRepository,
							  INurseRepository nurseRepository,
							  IClientRepository clientRepository,
							  ITreatmentScheduleRepository treatmentScheduleRepository,
							  IConsultationScheduleRepository consultationScheduleRepository, AvailableHoursService availableHoursService) {

		this.veterinarianRepository = veterinarianRepository;
		this.nurseRepository = nurseRepository;
		this.clientRepository = clientRepository;
		this.treatmentScheduleRepository = treatmentScheduleRepository;
		this.consultationScheduleRepository = consultationScheduleRepository;
		this.availableHoursService = availableHoursService;
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

		return nurseRepository.findById(nurse_id)
				.map(nurse -> createTreatmentSchedule(treatmentSchedule, client, nurse))
				.orElse(createTreatmentScheduleWithNoNurse(treatmentSchedule, client));

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

		return veterinarianRepository.findById(vet_id)
				.map(veterinarian -> createConsultationSchedule(consultationSchedule, client, veterinarian))
				.orElse(createConsultationScheduleWithNoVeterinarian(consultationSchedule, client));
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
			@RequestParam(name="veterinarian", required = false) int vet_id ) throws PersonNotFoundException, ScheduleConflictException {

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		return veterinarianRepository.findById(vet_id)
				.map(v -> createConsultationSchedule(consultationSchedule, client, v))
				.orElse(createConsultationScheduleWithNoVeterinarian(consultationSchedule, client));
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

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	@PostMapping(path = "/schedule/treatment/receptionist", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity receptionistScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="client") int client_id,
			@RequestParam(name="nurse", required = false) int nurse_id ) throws PersonNotFoundException, ScheduleConflictException {

		Client client = clientRepository.findById(client_id)
				.orElseThrow(() -> new PersonNotFoundException("Client not found."));

		return nurseRepository
				.findById(nurse_id)
				.map(n -> createTreatmentSchedule(treatmentSchedule, client, n))
				.orElse(createTreatmentScheduleWithNoNurse(treatmentSchedule, client));
	}

	private ResponseEntity createTreatmentSchedule(TreatmentSchedule treatmentSchedule,
												   Client client,
												   Nurse nurse) throws ScheduleConflictException{

		Calendar start = CalendarUtil.getStartDay(treatmentSchedule.getStartPeriod());
		long fStart = start.getTimeInMillis();
		long fEnd = CalendarUtil.getEndOfDayFromCalendar(start).getTimeInMillis();

		if(availableHoursService
				.getAvailableHoursForNurseBetween(nurse, fStart, fEnd)
				.stream()
				.noneMatch(treatmentSchedule::inside))
			throw new ScheduleConflictException("No nurse work hours available for this consultation schedule.");

		treatmentSchedule.setNurse(nurse);
		treatmentSchedule.setClient(client);
		treatmentSchedule = treatmentScheduleRepository.save(treatmentSchedule);

		return ResponseEntity.created(URI.create("/treatment_schedule/" + treatmentSchedule.getId())).build();
	}

	private ResponseEntity createConsultationSchedule(ConsultationSchedule consultationSchedule,
													  Client client,
													  Veterinarian veterinarian) throws ScheduleConflictException{

		Calendar start = CalendarUtil.getStartDay(consultationSchedule.getStartPeriod());
		long fStart = start.getTimeInMillis();
		long fEnd = CalendarUtil.getEndOfDayFromCalendar(start).getTimeInMillis();

		if(availableHoursService
				.getAvailableHoursForVeterinarianShift(veterinarian, fStart, fEnd)
				.stream()
				.noneMatch(consultationSchedule::inside))
			throw new ScheduleConflictException("No veterinarian work hours available for this consultation schedule.");


		consultationSchedule.setVeterinarian(veterinarian);
		consultationSchedule.setClient(client);
		consultationSchedule = consultationScheduleRepository.save(consultationSchedule);

		return ResponseEntity.created(URI.create("/consultation_schedule/" + consultationSchedule.getId())).build();
	}


	private ResponseEntity createConsultationScheduleWithNoVeterinarian(ConsultationSchedule consultationSchedule,
													  Client client) throws ScheduleConflictException {

		Calendar start = CalendarUtil.getStartDay(consultationSchedule.getStartPeriod());
		long fStart = start.getTimeInMillis();
		long fEnd = CalendarUtil.getEndOfDayFromCalendar(start).getTimeInMillis();

		VeterinarianShift vet = availableHoursService
				.getAllAvailableHoursForVeterinarianBetween(fStart, fEnd)
				.stream()
				.filter(consultationSchedule::inside)
				.findAny()
				.orElseThrow(() -> new ScheduleConflictException("No veterinarian work hours available for this consultation schedule."));

		consultationSchedule.setVeterinarian(vet.getVeterinarian());
		consultationSchedule.setClient(client);
		consultationSchedule = consultationScheduleRepository.save(consultationSchedule);

		return ResponseEntity.created(URI.create("/consultation_schedule/" + consultationSchedule.getId())).build();
	}

	private ResponseEntity createTreatmentScheduleWithNoNurse(TreatmentSchedule treatmentSchedule,
																		Client client) throws ScheduleConflictException {

		Calendar start = CalendarUtil.getStartDay(treatmentSchedule.getStartPeriod());
		long fStart = start.getTimeInMillis();
		long fEnd = CalendarUtil.getEndOfDayFromCalendar(start).getTimeInMillis();

		NurseShift nurseShift = availableHoursService
				.getAllAvailableHoursForNursesBetween(fStart, fEnd)
				.stream()
				.filter(treatmentSchedule::inside)
				.findAny()
				.orElseThrow(() -> new ScheduleConflictException("No nurse available for this treatment schedule."));

		treatmentSchedule.setNurse(nurseShift.getNurse());
		treatmentSchedule.setClient(client);
		treatmentSchedule = treatmentScheduleRepository.save(treatmentSchedule);

		return ResponseEntity.created(URI.create("/treatment_schedule/" + treatmentSchedule.getId())).build();
	}
}
