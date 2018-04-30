package pt.hospetall.web.medicalprocedure;

import pt.hospetall.web.pet.Pet;

import javax.persistence.*;

@Entity
@Table(name="MedicalProcedure")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class MedicalProcedure {

    @ManyToOne
    private Pet pet;


}
