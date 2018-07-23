package pt.hospetall.web.security;

import netscape.security.ForbiddenTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import pt.hospetall.web.model.Account;
import pt.hospetall.web.repository.IAccountRepository;
import pt.hospetall.web.security.authorities.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

	private final IAccountRepository accountRepository;

	@Autowired
	public UserDetailService(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> account = accountRepository.findByUsername(username);
		if(!account.isPresent())
			throw new RuntimeException("Authentication data is wrong.");
		User user = new User(username, "password", getAuthorities(account.get().getRoles()));
		return user;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(short roles){
		Collection<GrantedAuthority> authorities = new LinkedList<>();

		if((roles & 0x0008) != 0)
			authorities.add(new ClientAuthority());

		if((roles & 0x0007) != 0)
			authorities.add(new WorkerAuthority());
		if((roles & 0x0001) != 0)
			authorities.add(new ReceptionistAuthority());
		if((roles & 0x0002) != 0)
			authorities.add(new VeterinarianAuthority());
		if((roles & 0x0004) != 0)
			authorities.add(new NurseAuthority());

		if((roles & 0x000F) != 0)
			authorities.add(new UserAuthority());

		return authorities;
	}

}
