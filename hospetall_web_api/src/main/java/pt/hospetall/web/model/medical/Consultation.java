package pt.hospetall.web.model.medical;

import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.person.Veterinarian;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@PreAuthorize("hasAnyRole('ROLE_VETERINARIAN', 'ROLE_ADMIN')")
@Entity
public class Consultation extends MedicalProcedure {

	@ManyToOne
	private Veterinarian veterinarian;

	private float weight;

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}

	public float getWeight() {
		return weight;
	}
}
