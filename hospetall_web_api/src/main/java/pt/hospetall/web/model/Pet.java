package pt.hospetall.web.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Pet")
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

