package pt.hospetall.web.person.nurse;

import pt.hospetall.web.medicalprocedure.treatment.Treatment;
import pt.hospetall.web.person.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Nurse extends Person {

	@Id
	@GeneratedValue
	private int id;

	@OneToMany(mappedBy = "nurse")
	private Set<Treatment> treatments = new HashSet<>();

	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
