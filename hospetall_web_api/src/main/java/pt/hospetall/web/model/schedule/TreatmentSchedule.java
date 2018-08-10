package pt.hospetall.web.model.schedule;

import pt.hospetall.web.model.person.Nurse;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class TreatmentSchedule extends Schedule {

	@ManyToOne(targetEntity = Nurse.class)
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
