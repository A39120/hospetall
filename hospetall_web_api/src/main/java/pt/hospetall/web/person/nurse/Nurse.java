package pt.hospetall.web.person.nurse;

import pt.hospetall.web.person.Person;

import javax.persistence.Entity;

@Entity
public class Nurse extends Person {

	@OneToMany(name = "nurse")
	private Set<Treatment> treatments = new HashSet<>();
}
