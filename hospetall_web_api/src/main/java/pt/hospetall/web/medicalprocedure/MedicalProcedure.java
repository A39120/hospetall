package pt.hospetall.web.medicalprocedure;

import pt.hospetall.web.pet.Pet;

import javax.persistence.*;

@Entity
@Table(name="MedicalProcedure")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MedicalProcedure {

	@ManyToOne
	private Pet pet;
    
	@Id
	@GeneratedValue
	private int id; 
	private String case_history;
	private String diagnosis;
	private String treatment;
	private String observations;
}
