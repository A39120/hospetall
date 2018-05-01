package pt.hospetall.web.person.veterinarian;

import pt.hospetall.web.person.Person;

@Entity
@Table(name = "Veterinarian")
public class Veterinarian extends Person {
	
	@OneToMany(name = "veterinarian")
	Set<Consultation> consultations = new HashSet<>();	
}
