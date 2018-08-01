package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.hospetall.web.error.UsernameTakenException;
import pt.hospetall.web.model.security.Authority;
import pt.hospetall.web.model.security.Account;
import pt.hospetall.web.repository.IAuthorityRepository;
import pt.hospetall.web.repository.IUserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private final IUserRepository userRepository;
	private final IAuthorityRepository authorityRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public CustomUserDetailsService(IUserRepository userRepository, IAuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findUserByUsername(username)
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found."));
	}

	@Transactional
	public Account createUser(String username, String password, String... roles) throws UsernameTakenException{
		Account account = new Account();
		if(userRepository.findUserByUsername(username).isPresent())
			throw new UsernameTakenException("Username taken.");

		Set<Authority> set = Arrays.stream(roles)
				.map(authorityRepository::findByAuthority)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());

		account.setUsername(username);
		account.setPassword(passwordEncoder.encode(password));
		account.setAuthorities(set);
		userRepository.save(account);
		return account;
	}

	public Account createVeterinarian(String username, String password) throws UsernameTakenException {
		return createUser(username, password, "ROLE_VETERINARIAN", "ROLE_WORKER");
	}

	public Account createNurse(String username, String password) throws UsernameTakenException{
		return createUser(username, password, "ROLE_NURSE", "ROLE_WORKER");
	}

	public Account createReceptionist(String username, String password) throws UsernameTakenException {
		return createUser(username, password, "ROLE_RECEPTIONIST", "ROLE_WORKER");
	}

	public Account createClient(String username, String password) throws UsernameTakenException{
		return createUser(username, password, "ROLE_CLIENT");
	}

	private class CustomUserDetails implements UserDetails {

		private String username, password;
		private Collection<? extends GrantedAuthority> authorities;

		CustomUserDetails(Account account){
			username = account.getUsername();
			password = account.getPassword();
			authorities = account.getAuthorities();
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
	}
}
