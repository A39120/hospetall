package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.error.exceptions.AccountNotFoundException;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
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
import pt.hospetall.web.services.security.CustomUserDetailsService;
import pt.hospetall.web.services.EmailSenderService;

import java.net.URI;
import java.util.UUID;
import java.util.function.BiConsumer;

@RepositoryRestController
@RequestMapping
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

	@PreAuthorize("hasRole('ROLE_VETERINARIAN')")
	@PutMapping(path = "/alter/veterinarian")
	public ResponseEntity alterVeterinarian(
			@RequestBody Veterinarian veterinarian,
			Authentication authentication) throws UsernameTakenException, AccountNotFoundException, PersonNotFoundException {

		Veterinarian old = getPerson(authentication, veterinarianRepository);
		return ResponseEntity.ok(updatePerson(veterinarian, old, veterinarianRepository));
	}

	@PreAuthorize("hasRole('ROLE_RECEPTIONIST')")
	@PutMapping(path = "/alter/receptionist")
	public ResponseEntity alterReceptionist(
			@RequestBody Receptionist receptionist,
			Authentication authentication
	) throws UsernameTakenException, AccountNotFoundException, PersonNotFoundException {
		Receptionist old = getPerson(authentication, receptionistRepository);
		return ResponseEntity.ok(updatePerson(receptionist, old, receptionistRepository));
	}

	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PutMapping(path = "/alter/client")
	public ResponseEntity alterClient(
			@RequestBody Client client,
			Authentication authentication
	) throws UsernameTakenException, AccountNotFoundException, PersonNotFoundException {
		Client old = getPerson(authentication, clientRepository);
		return ResponseEntity.ok(updatePerson(client, old, clientRepository));
	}

	@PreAuthorize("hasRole('ROLE_NURSE')")
	@PutMapping(path = "/alter/nurse")
	public ResponseEntity alterNurse(
			@RequestBody Nurse nurse,
			Authentication authentication) throws UsernameTakenException, AccountNotFoundException, PersonNotFoundException {

		Nurse old = getPerson(authentication, nurseRepository);
		return ResponseEntity.ok(updatePerson(nurse, old, nurseRepository));
	}

	private String getAuthenticatedUsername(Authentication authentication){
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		return principal.getUsername();
	}

	private <T extends Person, V extends IPersonBaseRepository<T>> T getPerson(
			Authentication authentication,
			V repository) throws PersonNotFoundException{
		String email = getAuthenticatedUsername(authentication);
		return repository.findByEmail(email)
				.orElseThrow(PersonNotFoundException::new);
	}

	private <T extends Person, V extends IPersonBaseRepository<T>> T updatePerson(T person, T old, V repo){
		person.setId(old.getId());
		alterAccount(person, old.getEmail());
		return repo.save(person);
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

	/**
	 * Registers a person in the corresponding repository.
	 *
	 * @param person, might be a nurse, client etc.
	 * @param uri, the uri of the created person
	 * @param create how the person is created
	 * @return status code CREATED
	 */
	private ResponseEntity register(Person person, String uri, BiConsumer<String, String> create){

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

	/**
	 * Alters account username
	 *
	 * @param person the person that we are changing
	 * @param previous the previous email used by person
	 * @throws UsernameTakenException in case the new email is already taken by a person
	 * @throws AccountNotFoundException in case account has not been found
	 */
	private void alterAccount(Person person, String previous) throws UsernameTakenException, AccountNotFoundException {
		String email = person.getEmail();
		if(!email.equals(previous))
			userDetailsService.changeUsername(previous, person.getEmail());
	}

}
