package pt.hospetall.web.controller;

import org.apache.tomcat.util.http.parser.Authorization;
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
import pt.hospetall.web.model.medical.Consultation;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.repository.medical.IConsultationRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.pet.IPetRepository;

import java.net.URI;

@RepositoryRestController
@RequestMapping
public class RegisterConsultationController {

	@Autowired
	private IPetRepository petRepository;

	@Autowired
	private IConsultationRepository consultationRepository;

	@Autowired
	private IVeterinarianRepository veterinarianRepository;

	@PreAuthorize("hasAnyRole('ROLE_VETERINARIAN')")
	@PostMapping(path = "/register/consultation", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addConsultation(@RequestBody Consultation consultation,
										  @RequestParam("pet")int pet_id,
										  Authentication authentication){
		Pet pet = petRepository.findById(pet_id)
				.orElseThrow(PetNotFoundException::new);

		consultation.setPet(pet);

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();
		Veterinarian veterinarian = veterinarianRepository.findByEmail(email)
				.orElseThrow(() -> new PersonNotFoundException("Veterinarian not found."));

		consultation.setVeterinarian(veterinarian);
		consultation = consultationRepository.save(consultation);

		return ResponseEntity.created(URI.create("/consultation/" + consultation.getId())).build();
	}


}
