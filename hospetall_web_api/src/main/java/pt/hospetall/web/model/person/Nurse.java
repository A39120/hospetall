package pt.hospetall.web.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.Treatment;
import pt.hospetall.web.model.TreatmentSchedule;
import pt.hospetall.web.model.base.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Nurse extends Person {

	@JsonIgnore
	@OneToMany(mappedBy = "nurse")
	private Set<Treatment> treatments = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "nurse")
	private Set<TreatmentSchedule> schedule = new HashSet<>();

	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}

	public Set<TreatmentSchedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<TreatmentSchedule> schedule) {
		this.schedule = schedule;
	}
}


