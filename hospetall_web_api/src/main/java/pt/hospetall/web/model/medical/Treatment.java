package pt.hospetall.web.model.medical;

import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.pet.Pet;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Treatment extends MedicalProcedure {

	@ManyToOne
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
