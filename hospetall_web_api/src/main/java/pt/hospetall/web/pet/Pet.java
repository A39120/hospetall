package pt.hospetall.web.pet;

import pt.hospetall.web.client.Client;
import pt.hospetall.web.race.Race;
import pt.hospetall.web.species.Species;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Client owner;

    private String name;

    @ManyToOne
    private Race race;

    @ManyToOne
    private Species species;
    private Date birthdate;
    private int chip_number;
    private int license_number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
