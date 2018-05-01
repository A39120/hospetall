package pt.hospetall.web.pet;

import pt.hospetall.web.person.client.Client;
import pt.hospetall.web.medicalprocedure.MedicalProcedure;
import pt.hospetall.web.race.Race;
import pt.hospetall.web.schedule.Schedule;
import pt.hospetall.web.species.Species;
import pt.hospetall.web.waitingroom.WaitingRoom;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Pet")
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

	//@OneToMany(mappedBy = "")
	//private Set<Schedule> schedules = new HashSet<>();

	//@ManyToOne
	//private WaitingRoom waitingRoom;

	@OneToMany(mappedBy = "pet")
	private Set<MedicalProcedure> procedures = new HashSet<>();

}
