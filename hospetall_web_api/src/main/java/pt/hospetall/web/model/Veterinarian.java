package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.Person;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Veterinarian")
public class Veterinarian extends Person {

	@JsonIgnore
	@OneToMany(mappedBy = "veterinarian")
	private Set<Consultation> consultations = new HashSet<>();

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}
}