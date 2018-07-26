package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(ServerSecurityConfig.class)
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final TokenStore tokenStore;

	@Autowired
	public AuthorizationConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailServiceImpl userDetailsService, TokenStore tokenStore) {
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.tokenStore = tokenStore;
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
				.passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("client_id")
				.authorizedGrantTypes("authorization_code", "password", "implicit")
				.authorities("ROLE_ADMIN", "ROLE_WORKER", "ROLE_RECEPTIONIST", "ROLE_CLIENT", "ROLE_VETERINARIAN", "ROLE_NURSE")
				.secret("secret")
				.scopes("read", "write", "trust")
				.accessTokenValiditySeconds(600)
				.refreshTokenValiditySeconds(1200);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService)
				.tokenStore(tokenStore);
	}
}
