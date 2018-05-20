package pt.hospetall.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan
@EnableWebMvc
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
class WebMvcConfiguration extends WebMvcConfigurationSupport {

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer c) {
		c.defaultContentType(MediaType.APPLICATION_JSON);
	}

}
