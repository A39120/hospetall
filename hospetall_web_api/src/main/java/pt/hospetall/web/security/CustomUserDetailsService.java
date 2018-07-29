package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IAuthorityRepository authorityRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//createAuthoritiesIfEmpty();

		return userRepository.findUserByUsername(username)
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found."));
	}

	public void createAuthoritiesIfEmpty(){
		if(!authorityRepository.findAll().isEmpty())
			return;

		String[] roles = {"ROLE_CLIENT", "ROLE_ADMIN", "ROLE_VETERINARIAN", "ROLE_NURSE", "ROLE_WORKER", "ROLE_RECEPTIONIST"};
		Arrays.stream(roles)
				.forEach(role -> {
					Authority auth = new Authority();
					auth.setAuthority(role);
					authorityRepository.save(auth);
				});
	}

	public Account createClient(String username, String password) {
		return createUser(username, password, "ROLE_CLIENT");
	}

	public Account createUser(String username, String password, String... roles) {
		Account account = new Account();
		if(userRepository.findUserByUsername(username).isPresent())
			throw new IllegalArgumentException("Username taken.");

		Set<Authority> set = Arrays.stream(roles)
				.map((role) -> authorityRepository.findByAuthority(role))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());

		account.setUsername(username);
		account.setPassword(password);
		//account.setPassword(passwordEncoder.encode(password));
		account.setAuthorities(set);

		return account;
	}

	public Account createVeterinarian(String username, String password){
		return createUser(username, password, "ROLE_VETERINARIAN", "ROLE_WORKER");
	}

	public Account createNurse(String username, String password){
		return createUser(username, password, "ROLE_NURSE", "ROLE_WORKER");
	}

	public Account createReceptionist(String username, String password){
		return createUser(username, password, "ROLE_RECEPTIONIST", "ROLE_WORKER");
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
