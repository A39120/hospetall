package pt.hospetall.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.MedicalProcedure;
import pt.hospetall.web.model.base.NameBaseEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pet extends NameBaseEntity {

	@ManyToOne
	@JsonIgnore
	private Client owner;

	@ManyToOne
	@JsonIgnore
	private Race race;

	@ManyToOne
	@JsonIgnore
	private Species species;

	private Date birthdate;
	private int chip_number;
	private int license_number;

	@OneToMany(mappedBy = "pet")
	@JsonIgnore
	private Set<Consultation> consultations = new HashSet<>();

	public Set<Consultation> getMedicalProcedures() {
		return consultations;
	}

	public void setMedicalProcedures(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Species getSpecies() {
		return species;
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

