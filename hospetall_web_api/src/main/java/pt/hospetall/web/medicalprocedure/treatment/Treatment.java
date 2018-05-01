package pt.hospetall.web.medicalprocedure.treatment;

import pt.hospetall.web.medicalprocedure.MedicalProcedure;

import javax.persistence.Entity;

@Entity
@Table(name = "Treatment")
public class Treatment extends MedicalProcedure {
	
	@ManyToOne
	private Nurse nurse;
	
}
