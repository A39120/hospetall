package pt.hospetall.web.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class NurseAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "NURSE_ROLE";
	}

}
