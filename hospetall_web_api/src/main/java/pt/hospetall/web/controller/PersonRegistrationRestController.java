package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.error.exceptions.UsernameTakenException;
import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Receptionist;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.person.base.Person;
import pt.hospetall.web.repository.base.IPersonBaseRepository;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IReceptionistRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.services.CustomUserDetailsService;
import pt.hospetall.web.services.EmailSenderService;

import java.net.URI;
import java.util.UUID;
import java.util.function.BiConsumer;

@RestController
public class PersonRegistrationRestController {
	private final CustomUserDetailsService userDetailsService;
	private final IClientRepository clientRepository;
	private final EmailSenderService senderService;
	private final IVeterinarianRepository veterinarianRepository;
	private final INurseRepository nurseRepository;
	private final IReceptionistRepository receptionistRepository;

	@Autowired
	public PersonRegistrationRestController(CustomUserDetailsService userDetailsService,
											IClientRepository clientRepository,
											EmailSenderService senderService,
											IVeterinarianRepository veterinarianRepository,
											INurseRepository nurseRepository,
											IReceptionistRepository receptionistRepository) {

		this.userDetailsService = userDetailsService;
		this.clientRepository = clientRepository;
		this.senderService = senderService;
		this.veterinarianRepository = veterinarianRepository;
		this.nurseRepository = nurseRepository;
		this.receptionistRepository = receptionistRepository;
	}

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	@PostMapping(path = "/register/client")
	public ResponseEntity registerClient(@RequestBody Client client) throws UsernameTakenException {
		//Check if client already exists
		client = ifExists(client, clientRepository);
		return register(client, "client", userDetailsService::createClient);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/register/veterinarian")
	public ResponseEntity registerVeterinarian(@RequestBody Veterinarian veterinarian) throws UsernameTakenException {
		veterinarian = ifExists(veterinarian, veterinarianRepository);
		return register(veterinarian, "veterinarian", userDetailsService::createVeterinarian);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/register/nurse")
	public ResponseEntity registerNurse(@RequestBody Nurse nurse) throws UsernameTakenException {
		nurse = ifExists(nurse, nurseRepository);
		return register(nurse, "nurse", userDetailsService::createNurse);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path= "/register/receptionist")
	public ResponseEntity registerReceptionist(@RequestBody Receptionist receptionist) throws UsernameTakenException {
		receptionist = ifExists(receptionist, receptionistRepository);
		return register(receptionist, "receptionist", userDetailsService::createReceptionist);
	}

	/**
	 * Checks if person based entity exists in it's repository.
	 *
	 * @param person person based entity
	 * @param repository person repository based
	 * @param <T> class that extends from Person
	 * @param <V> class that extends from person repository
	 * @return the new person with set id
	 * @throws UsernameTakenException in case the person already is in the database
	 */
	private <T extends Person, V extends IPersonBaseRepository<T>> T ifExists(T person, V repository)
			throws UsernameTakenException {
		repository
				.findByEmail(person.getEmail())
				.ifPresent((c) -> {throw new UsernameTakenException("Email already taken.");});

		return repository.save(person);
	}


	private ResponseEntity register(Person person, String uri, BiConsumer<String, String> create){
		//This is not the final password, this is just the temporary password to be big and strong, which might
		//tire the user when introducing the password for the first time. Not my best idea for password generation
		String password = UUID
				.randomUUID()
				.toString()
				.substring(0, 6);

		create.accept(person.getEmail(), password);
		senderService.sendSimpleMessage(person.getEmail(),
				"Welcome to HosPetAll.",
				"The password to access your account is: " + password);

		return ResponseEntity
				.created(URI.create("/" + uri + "/" + person.getId()))
				.build();
	}

}
