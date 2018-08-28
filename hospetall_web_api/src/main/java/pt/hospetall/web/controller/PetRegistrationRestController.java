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
import pt.hospetall.web.error.exceptions.PetConflictException;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.pet.IPetRepository;

import java.net.URI;

@RepositoryRestController
@RequestMapping
public class PetRegistrationRestController {

	private final IPetRepository petRepository;
	private final IClientRepository clientRepository;

	@Autowired
	public PetRegistrationRestController(IPetRepository petRepository,
										 IClientRepository clientRepository) {
		this.petRepository = petRepository;
		this.clientRepository = clientRepository;
	}

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	@PostMapping(path = "/register/client/{id}/pet", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registerPet(
			@PathVariable(name="id") int client_id,
			@RequestBody Pet pet)
			throws PersonNotFoundException, PetConflictException {

		return clientRepository.findById(client_id)
				.map(client -> {
					pet.setOwner(client);
					client.getPets()
							.stream()
							.filter(p -> pet.getName().equals(p.getName()))
							.findAny()
							.ifPresent((p) -> { throw new PetConflictException(); });

					Pet newPet = petRepository.save(pet);
					URI uri = URI.create("/pet/" + newPet.getId());
					return ResponseEntity.created(uri).build();
				})
				.orElseThrow(PersonNotFoundException::new);

	}
}
