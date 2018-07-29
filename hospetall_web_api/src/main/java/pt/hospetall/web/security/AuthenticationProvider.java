package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private final CustomUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if(authentication.getCredentials() == null && userDetails.getPassword() == null)
			throw new BadCredentialsException("Please specify credentials");

		if(passwordEncoder.matches((CharSequence) authentication.getCredentials(), userDetails.getPassword()))
			throw new BadCredentialsException("Invalid login");

		return;
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		return userDetailsService.loadUserByUsername(username);
	}

}
