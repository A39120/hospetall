package pt.hospetall.web.model;

import pt.hospetall.web.model.base.MedicalProcedure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Treatment extends MedicalProcedure {

	@NotNull
	@ManyToOne
	private Pet pet;

	@NotNull
	@ManyToOne
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	@Override
	public Pet getPet() {
		return pet;
	}

	@Override
	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
