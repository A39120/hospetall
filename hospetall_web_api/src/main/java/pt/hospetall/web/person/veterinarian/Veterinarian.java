package pt.hospetall.web.person.veterinarian;

import pt.hospetall.web.medicalprocedure.consultation.Consultation;
import pt.hospetall.web.person.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Veterinarian")
@PrimaryKeyJoinColumn(name = "person_id")
public class Veterinarian extends Person {

	@Id
	@GeneratedValue
	private int id;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	@OneToMany(mappedBy = "veterinarian")
	Set<Consultation> consultations = new HashSet<>();

	public Set<Consultation> getConsultations() {
		return consultations;
	}

}
