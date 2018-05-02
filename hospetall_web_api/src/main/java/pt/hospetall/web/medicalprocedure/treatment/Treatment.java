package pt.hospetall.web.medicalprocedure.treatment;

import pt.hospetall.web.medicalprocedure.MedicalProcedure;
import pt.hospetall.web.person.nurse.Nurse;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Treatment")
public class Treatment extends MedicalProcedure {
	
	@ManyToOne
	private Nurse nurse;
	
}
