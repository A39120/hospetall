package pt.hospetall.web.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import pt.hospetall.web.model.base.BaseEntity;

@Configuration
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(BaseEntity.class);
		config.useHalAsDefaultJsonMediaType(true);
	}
}
