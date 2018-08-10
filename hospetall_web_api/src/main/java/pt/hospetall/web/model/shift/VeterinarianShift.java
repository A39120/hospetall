package pt.hospetall.web.model.shift;

import pt.hospetall.web.model.person.Veterinarian;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class VeterinarianShift extends Shift{

	@ManyToOne(targetEntity = Veterinarian.class)
	private Veterinarian veterinarian;

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}
}
