package pt.hospetall.web.model;

import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.pet.Pet;

import javax.persistence.*;

@Entity
public class WaitingListConsultation extends BaseEntity {


    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToOne
    @JoinColumn(name = "vet_id")
    private Veterinarian vet;


    private long time;
    private int patientPriority;



    public void setVet(Veterinarian vet) {
        this.vet = vet;
    }

    public Veterinarian getVet() {
        return vet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }




    public int getPatientPriority() {
        return patientPriority;
    }

    public void setPatientPriority(int patientPriority) {
        this.patientPriority = patientPriority;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
