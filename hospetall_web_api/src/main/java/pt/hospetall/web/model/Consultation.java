package pt.hospetall.web.model;

import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.base.MedicalProcedure;
import pt.hospetall.web.model.person.Veterinarian;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@PreAuthorize("hasAnyRole('ROLE_VETERINARIAN', 'ROLE_ADMIN')")
@Entity
public class Consultation extends MedicalProcedure {

	@NotNull
	@ManyToOne
	private Veterinarian veterinarian;

	private float weight, temperature, heartRhythm;

	@PreAuthorize("isAuthenticated()")
	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}

	@PreAuthorize("isAuthenticated()")
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getHeartRhythm() {
		return heartRhythm;
	}

	public void setHeartRhythm(float heartRhythm) {
		this.heartRhythm = heartRhythm;
	}
}
