package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.base.NameBaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@PreAuthorize("isAuthenticated()")
public class Pet extends NameBaseEntity {

	@ManyToOne
	private Client owner;

	@ManyToOne
	private Race race;

	@ManyToOne
	private Species species;

	private Date birthdate;
	private int chip_number;
	private int license_number;

	@OneToMany(mappedBy = "pet")
	@JsonIgnore
	private Set<Consultation> consultations = new HashSet<>();

	@OneToMany(mappedBy = "pet")
	@JsonIgnore
	private Set<Treatment> treatments = new HashSet<>();

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public String getRace() {
		return race.getName();
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public String getSpecies() {
		return species.getName();
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getChip_number() {
		return chip_number;
	}

	public void setChip_number(int chip_number) {
		this.chip_number = chip_number;
	}

	public int getLicense_number() {
		return license_number;
	}

	public void setLicense_number(int license_number) {
		this.license_number = license_number;
	}
}

