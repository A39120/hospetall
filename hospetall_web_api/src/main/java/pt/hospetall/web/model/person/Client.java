package pt.hospetall.web.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.person.base.Person;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.pet.Pet;
import pt.hospetall.web.model.schedule.TreatmentSchedule;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client extends Person {

	@Column(name = "address", nullable = true)
	private String address;

	@Column(name = "postal_code")
	private String postalCode;

	private String other;

	@Column(name="nif", unique=true, length = 9)
	private int nif;

	@JsonIgnore
	@OneToMany(mappedBy = "owner")
	private Set<Pet> pets = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private Set<ConsultationSchedule> consultationSchedules = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "client")
	private Set<TreatmentSchedule> treatmentSchedules = new HashSet<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	public Set<ConsultationSchedule> getConsultationSchedules() {
		return consultationSchedules;
	}

	public void setConsultationSchedules(Set<ConsultationSchedule> consultationSchedules) {
		this.consultationSchedules = consultationSchedules;
	}

	public Set<TreatmentSchedule> getTreatmentSchedules() {
		return treatmentSchedules;
	}

	public void setTreatmentSchedules(Set<TreatmentSchedule> treatmentSchedules) {
		this.treatmentSchedules = treatmentSchedules;
	}


}

