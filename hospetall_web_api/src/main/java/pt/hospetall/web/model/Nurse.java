package pt.hospetall.web.model;

import pt.hospetall.web.model.base.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Nurse extends Person {

	@OneToMany(mappedBy = "nurse")
	private Set<Treatment> treatments = new HashSet<>();

	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}
}
