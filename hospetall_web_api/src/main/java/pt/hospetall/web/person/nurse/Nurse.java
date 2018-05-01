package pt.hospetall.web.person.nurse;

import pt.hospetall.web.medicalprocedure.treatment.Treatment;
import pt.hospetall.web.person.Person;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Nurse extends Person {

	@OneToMany(mappedBy = "nurse")
	private Set<Treatment> treatments = new HashSet<>();
}
