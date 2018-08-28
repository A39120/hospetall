package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.security.IUserRepository;
import pt.hospetall.web.services.security.CustomUserDetailsService;

@PreAuthorize("isAuthenticated()")
@RepositoryRestController
@RequestMapping
public class ClientController {

	private final IUserRepository userRepository;
	private final CustomUserDetailsService userDetailsService;
	private final IClientRepository clientRepository;

	@Autowired
	public ClientController(IUserRepository userRepository,
							CustomUserDetailsService userDetailsService,
							IClientRepository clientRepository) {
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.clientRepository = clientRepository;
	}

	/**
	 * Redirects the authenticated client to it's information.
	 * @param authentication - the authenticated client
	 * @return the redirection to the client information
	 * @throws PersonNotFoundException in case there is no client in the database.
	 */
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@GetMapping(path = "/self/client")
	public String getClientSelf(Authentication authentication) throws PersonNotFoundException {
		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();
		return "redirect:/client/" + clientRepository.findByEmail(email)
				.map(Client::getId)
				.orElseThrow(() -> new AccessDeniedException("Access denied for this user."));
	}

	/**
	 * Updates the authenticated client with it's new information. If the email has changed then the authentication
	 * information also has changed.
	 * @param newClient - the new client info that will replace the old one.
	 * @param authentication - the authenticated client
	 * @return Response with status 200, if updated
	 * @throws PersonNotFoundException - Client is not in the repository
	 */
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PutMapping(path = "/self/client")
	public ResponseEntity updateClientValue(
			@RequestBody Client newClient,
			Authentication authentication) throws PersonNotFoundException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();

		return clientRepository.findByEmail(email)
				.map((client) -> {
					if(!client.getEmail().equals(newClient.getEmail()))
						userDetailsService.changeUsername(email, newClient.getEmail());

					clientRepository.save(newClient);
					return ResponseEntity.ok().build();
				})
				.orElseThrow(PersonNotFoundException::new);
	}

}
