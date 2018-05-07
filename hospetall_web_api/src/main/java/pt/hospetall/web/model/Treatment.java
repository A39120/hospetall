package pt.hospetall.web.model;

import pt.hospetall.web.model.base.MedicalProcedure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Treatment extends MedicalProcedure {

	@NotNull
	@ManyToOne
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
