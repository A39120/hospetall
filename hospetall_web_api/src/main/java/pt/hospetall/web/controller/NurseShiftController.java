package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.INurseShiftRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.services.ShiftService;

import java.net.URI;

@RepositoryRestController
@RequestMapping
public class NurseShiftController {

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private INurseShiftRepository nurseShiftRepository;

	@Autowired
	private INurseRepository nurseRepository;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(path = "/schedule/nurse/{id}/shift", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity schedule(
			@RequestBody NurseShift nurseShift,
			@PathVariable(name = "id") int id
	) throws PersonNotFoundException {
		Nurse nurse = nurseRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);

		nurseShift.setNurse(nurse);
		nurseShift = shiftService.addShift(nurseShift, nurse.getShifts(), nurseShiftRepository);
		return ResponseEntity.created(URI.create("/nurse_shift/" + nurseShift.getId())).build();
	}



}
