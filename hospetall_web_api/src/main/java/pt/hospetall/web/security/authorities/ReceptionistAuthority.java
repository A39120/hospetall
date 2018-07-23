package pt.hospetall.web.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class ReceptionistAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "RECEPTIONIST_ROLE";
	}

}
