package pt.hospetall.web.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.medical.Treatment;
import pt.hospetall.web.model.person.base.Person;
import pt.hospetall.web.model.shift.NurseShift;
import pt.hospetall.web.model.schedule.TreatmentSchedule;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "nurse")
public class Nurse extends Person {

	@JsonIgnore
	@OneToMany(mappedBy = "nurse")
	private Set<NurseShift> shifts;

	@JsonIgnore
	@OneToMany(mappedBy = "nurse", targetEntity = Treatment.class)
	private Set<Treatment> treatments;

	@JsonIgnore
	@OneToMany(mappedBy = "nurse", targetEntity = TreatmentSchedule.class)
	private Set<TreatmentSchedule> schedules;

	public Set<NurseShift> getShifts() {
		return shifts;
	}

	public void setShifts(Set<NurseShift> shifts) {
		this.shifts = shifts;
	}

	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}

	public Set<TreatmentSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<TreatmentSchedule> schedules) {
		this.schedules = schedules;
	}
}


