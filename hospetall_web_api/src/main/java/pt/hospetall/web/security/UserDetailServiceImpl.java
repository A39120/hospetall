package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import pt.hospetall.web.repository.IAccountRepository;

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
				.map(user -> new User(user.getUsername(), user.getPassword(), user.getAuthorities()))
				.orElseThrow(() -> new UsernameNotFoundException("Account not found."));
	}

}
