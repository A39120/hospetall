package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.error.UsernameTakenException;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.model.base.Person;
import pt.hospetall.web.repository.IClientRepository;
import pt.hospetall.web.services.CustomUserDetailsService;
import pt.hospetall.web.services.EmailSenderService;

import java.net.URI;
import java.util.UUID;

@RestController
public class RegistrationRestController {
	private final CustomUserDetailsService userDetailsService;
	private final IClientRepository clientRepository;
	private final EmailSenderService senderService;

	@Autowired
	public RegistrationRestController(CustomUserDetailsService userDetailsService,
									  IClientRepository clientRepository,
									  EmailSenderService senderService) {

		this.userDetailsService = userDetailsService;
		this.clientRepository = clientRepository;
		this.senderService = senderService;
	}

	@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_ADMIN')")
	@PostMapping(path = "/register/client")
	public ResponseEntity registerClient(@RequestBody Client client) throws UsernameTakenException {
		//Check if client already exists
		clientRepository
				.findByEmail(client.getEmail())
				.ifPresent((client1) ->{ throw new UsernameTakenException("Client email already exists.");});

		client = clientRepository.save(client);
		return register(client, "client");
	}

	private ResponseEntity register(Person person, String uri){
		//This is not the final password, this is just the temporary password to be big and strong, which might
		//tire the user when introducing the password for the first time. Not my best idea for password generation
		//String password = passwordEncoder.encode(UUID.randomUUID().toString());
		String password = UUID
				.randomUUID()
				.toString()
				.substring(0, 6);

		userDetailsService.createClient(person.getEmail(), password);
		senderService.sendSimpleMessage(person.getEmail(),
				"Welcome to HosPetAll.",
				"The password to access your account is: " + password);

		return ResponseEntity
				.created(URI.create("/" + uri + "/" + person.getId()))
				.build();
	}

}
