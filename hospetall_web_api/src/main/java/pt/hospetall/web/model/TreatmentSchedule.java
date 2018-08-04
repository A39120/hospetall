package pt.hospetall.web.model;

import pt.hospetall.web.model.base.Schedule;
import pt.hospetall.web.model.person.Nurse;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TreatmentSchedule")
public class TreatmentSchedule extends Schedule {

	@ManyToOne
	private Nurse nurse;

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

}
