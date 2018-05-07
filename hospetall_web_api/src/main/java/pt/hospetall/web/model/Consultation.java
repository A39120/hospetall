package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.MedicalProcedure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Consultation extends MedicalProcedure {

	@NotNull
	@JsonIgnore
	@ManyToOne
	private Veterinarian veterinarian;

	private float weight, temperature, heartRhythm;

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}

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
