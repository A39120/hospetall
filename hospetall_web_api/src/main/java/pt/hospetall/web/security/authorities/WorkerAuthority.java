package pt.hospetall.web.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public class WorkerAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return "WORKER_ROLE";
	}

}
