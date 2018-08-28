package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.model.person.base.Person;
import pt.hospetall.web.repository.person.IClientRepository;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.person.IReceptionistRepository;
import pt.hospetall.web.repository.person.IVeterinarianRepository;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping
public class GetSelfRoleController {

	private final IVeterinarianRepository veterinarianRepository;
	private final INurseRepository nurseRepository;
	private final IReceptionistRepository receptionistRepository;
	private final IClientRepository clientRepository;

	@Autowired
	public GetSelfRoleController(IVeterinarianRepository veterinarianRepository, INurseRepository nurseRepository, IReceptionistRepository receptionistRepository, IClientRepository clientRepository) {
		this.veterinarianRepository = veterinarianRepository;
		this.nurseRepository = nurseRepository;
		this.receptionistRepository = receptionistRepository;
		this.clientRepository = clientRepository;
	}


	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/self/role", produces = MediaTypes.HAL_JSON_VALUE)
	public ResponseEntity getRole(Authentication authentication){

		List<String> authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(authorities);
	}



	@PreAuthorize("isAuthenticated()")
	@GetMapping(path = "/self/info")
	public String getSelfInfo(Authentication authentication) throws AccessDeniedException, PersonNotFoundException{

		String email = ((UserDetails)authentication.getPrincipal()).getUsername();

		List<String> authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		Person person = null;
		String redirect = "";
		if(authorities.contains("ROLE_RECEPTIONIST")) {
			redirect = "redirect:/receptionist/";
			person = receptionistRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
		}
		else if(authorities.contains("ROLE_VETERINARIAN")) {
			redirect = "redirect:/veterinarian/";
			person = veterinarianRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
		}
		else if(authorities.contains("ROLE_NURSE")) {
			redirect = "redirect:/nurse/";
			person = nurseRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
		} else if(authorities.contains("ROLE_CLIENT")){
			redirect = "redirect:/client/";
			person = clientRepository.findByEmail(email).orElseThrow(PersonNotFoundException::new);
		}
		else
			throw new AccessDeniedException("Entity doesn't have information");

		return redirect + person.getId();
	}

}
