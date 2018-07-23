package pt.hospetall.web.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "USER_ROLE";
	}

}
