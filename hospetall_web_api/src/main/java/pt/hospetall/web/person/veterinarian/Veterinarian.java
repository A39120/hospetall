package pt.hospetall.web.person.veterinarian;

import pt.hospetall.web.medicalprocedure.consultation.Consultation;
import pt.hospetall.web.person.Person;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Veterinarian")
public class Veterinarian extends Person {
	
	@OneToMany(mappedBy = "veterinarian")
	Set<Consultation> consultations = new HashSet<>();
}
