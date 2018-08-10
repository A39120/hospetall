package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.error.exceptions.ScheduleConflictException;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.schedule.ITreatmentScheduleRepository;

@RepositoryRestController
@RequestMapping
public class ScheduleController {

	private final IVeterinarianRepository veterinarianRepository;
	private final INurseRepository nurseRepository;
	private final IClientRepository clientRepository;
	private final ITreatmentScheduleRepository treatmentScheduleRepository;

	@Autowired
	public ScheduleController(IVeterinarianRepository veterinarianRepository,
							  INurseRepository nurseRepository,
							  IClientRepository clientRepository, ITreatmentScheduleRepository treatmentScheduleRepository) {

		this.veterinarianRepository = veterinarianRepository;
		this.nurseRepository = nurseRepository;
		this.clientRepository = clientRepository;
		this.treatmentScheduleRepository = treatmentScheduleRepository;
	}


	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(path = "/schedule/treatment")
	public ResponseEntity clientScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="nurse", required = false) int nurse_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		//TODO

		return ResponseEntity.ok().build();
	}


	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping(path = "/schedule/consultation")
	public ResponseEntity clientScheduleConsultation(
			@RequestBody ConsultationSchedule consultationSchedule,
			@RequestParam(name="veterinarian", required = false) int vet_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		//TODO
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_NURSE')")
	@PostMapping(path = "/nurse/schedule/treatment")
	public ResponseEntity nurseScheduleTreatment(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@RequestParam(name="client", required = false) int client_id,
			Authentication authentication) throws PersonNotFoundException, ScheduleConflictException {

		//TODO
		return ResponseEntity.ok().build();

	}

}
