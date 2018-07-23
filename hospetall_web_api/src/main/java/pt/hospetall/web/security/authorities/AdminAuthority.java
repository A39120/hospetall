package pt.hospetall.web.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "ADMIN_ROLE";
	}

}
