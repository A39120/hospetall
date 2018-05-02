package pt.hospetall.web.medicalprocedure.consultation;

import pt.hospetall.web.medicalprocedure.MedicalProcedure;
import pt.hospetall.web.person.veterinarian.Veterinarian;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Consultation")
public class Consultation extends MedicalProcedure {

	@ManyToOne
	private Veterinarian veterinarian;
	
	private float weight;
	private float temperature;
	private float heart_rythim;
}
