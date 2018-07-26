package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.hospetall.web.model.security.Account;
import pt.hospetall.web.repository.IAccountRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final IAccountRepository accountRepository;

	@Autowired
	public UserDetailServiceImpl(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepository
				.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), getUserAuthorities(user)))
				.orElseThrow(() -> new UsernameNotFoundException("Account not found."));
	}

	private Set<GrantedAuthority> getUserAuthorities(Account account){
		HashSet<GrantedAuthority> authorities = new HashSet<>();
		short roles = account.getRoles();

		if((roles & 0x1) != 0)
			authorities.add(() -> "ROLE_VETERINARIAN");

		if((roles & 0x2) != 0)
			authorities.add(() -> "ROLE_RECEPTIONIST");

		if((roles & 0x4) != 0)
			authorities.add(() -> "ROLE_NURSE");

		if((roles & 0x8) != 0)
			authorities.add(() -> "ROLE_CLIENT");

		if((roles & 0xF) == 0xF)
			authorities.add(() -> "ROLE_ADMIN");

		if((roles & 0x7) != 0)
			authorities.add(() -> "ROLE_WORKER");

		return authorities;
	}

}
