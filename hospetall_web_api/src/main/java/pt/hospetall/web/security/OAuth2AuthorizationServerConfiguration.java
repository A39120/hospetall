package pt.hospetall.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public OAuth2AuthorizationServerConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				.inMemory()
				.withClient("android_client")
				.authorizedGrantTypes("password")
				.authorities("ROLE_CLIENT", "ROLE_ADMIN")
				.scopes("read", "write")
				.secret(passwordEncoder.encode("android_secret_6749"))
			.and()
				.withClient("web_client")
				.authorizedGrantTypes("client_credentials", "password", "implicit")
				.authorities("ROLE_RECEPTIONIST", "ROLE_WORKER", "ROLE_NURSE", "ROLE_VETERINARIAN", "ROLE_ADMIN")
				.scopes("read", "write", "trusted")
				.secret(passwordEncoder.encode("web_secret"))
				.accessTokenValiditySeconds(3600)
				.refreshTokenValiditySeconds(36000);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
	}
}
