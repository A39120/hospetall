package pt.hospetall.web.medicalprocedure.consultation;

import pt.hospetall.web.medicalprocedure.MedicalProcedure;

import javax.persistence.Entity;

@Entity
@Table(name = "Consultation")
public class Consultation extends MedicalProcedure {

	@ManyToOne
	private Veterinarian veterinarian;
	
	private float weight;
	private float temperature;
	private float heart_rythim;
}
