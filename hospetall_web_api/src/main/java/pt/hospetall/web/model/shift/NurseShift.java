package pt.hospetall.web.model.shift;

import pt.hospetall.web.model.base.Period;
import pt.hospetall.web.model.person.Nurse;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class NurseShift extends Shift{

	@ManyToOne
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
