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
import pt.hospetall.web.error.exceptions.PetNotFoundException;
import pt.hospetall.web.model.medical.Treatment;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.repository.medical.ITreatmentRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.pet.IPetRepository;

import java.net.URI;

@RepositoryRestController
@RequestMapping
public class RegisterTreatmentController {

	private IPetRepository petRepository;
	private ITreatmentRepository treatmentRepository;
	private INurseRepository nurseRepository;

	@Autowired
	public RegisterTreatmentController(IPetRepository petRepository,
									   ITreatmentRepository treatmentRepository,
									   INurseRepository nurseRepository) {
		this.petRepository = petRepository;
		this.treatmentRepository = treatmentRepository;
		this.nurseRepository = nurseRepository;
	}

	@PreAuthorize("hasAnyRole('ROLE_NURSE')")
	@PostMapping(path = "/register/treatment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addTreatment(@RequestBody Treatment treatment,
										  @RequestParam("pet")int pet_id,
										  Authentication authentication){
		Pet pet = petRepository.findById(pet_id)
				.orElseThrow(PetNotFoundException::new);

		treatment.setPet(pet);

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();
		Nurse nurse = nurseRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Nurse not found."));

		treatment.setNurse(nurse);
		treatment = treatmentRepository.save(treatment);

		return ResponseEntity.created(URI.create("/treatment/" + treatment.getId())).build();
	}

}
