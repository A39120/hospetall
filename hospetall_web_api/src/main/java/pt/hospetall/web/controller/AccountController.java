package pt.hospetall.web.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.hospetall.web.error.exceptions.AccountNotFoundException;
import pt.hospetall.web.model.security.Account;
import pt.hospetall.web.services.CustomUserDetailsService;

@RepositoryRestController
@RequestMapping
public class AccountController {

	private final CustomUserDetailsService userDetailsService;

	@Autowired
	public AccountController(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	@PreAuthorize("isAuthenticated()")
	@PutMapping(path = "/self/account/password")
	public ResponseEntity changePassword(@RequestBody String password, Authentication authentication)
			throws BadCredentialsException, AccountNotFoundException {

		UserDetails principal = (UserDetails) authentication.getPrincipal();
		String email = principal.getUsername();
		userDetailsService.changePassword(email, password);
		return ResponseEntity.ok().build();
	}

}
