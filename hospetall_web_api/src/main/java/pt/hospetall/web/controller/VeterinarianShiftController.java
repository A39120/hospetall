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
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.shift.VeterinarianShift;
import pt.hospetall.web.repository.IVeterinarianShiftRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.services.ShiftService;

import java.net.URI;

@RepositoryRestController
@RequestMapping
public class VeterinarianShiftController {

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private IVeterinarianShiftRepository veterinarianShiftRepository;

	@Autowired
	private IVeterinarianRepository veterinarianRepository;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(path = "/schedule/veterinarian/{id}/shift", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity schedule(
			@RequestBody VeterinarianShift veterinarianShift,
			@PathVariable(name = "id") int id
	) throws PersonNotFoundException {
		Veterinarian veterinarian = veterinarianRepository.findById(id)
				.orElseThrow(PersonNotFoundException::new);

		veterinarianShift.setVeterinarian(veterinarian);
		veterinarianShift = shiftService.addShift(veterinarianShift, veterinarian.getShifts(), veterinarianShiftRepository);
		return ResponseEntity.created(URI.create("/veterinarian_shift/" + veterinarianShift.getId())).build();
	}
}
