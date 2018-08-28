package pt.hospetall.web.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.model.medical.Consultation;
import pt.hospetall.web.model.medical.Treatment;
import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.model.schedule.TreatmentSchedule;

@Configuration
public class RestRepositoryConfiguration extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(
				Nurse.class,
				Veterinarian.class,
				Client.class,
				Pet.class,
				Consultation.class,
				Treatment.class
		);
		config.useHalAsDefaultJsonMediaType(true);
	}
}
