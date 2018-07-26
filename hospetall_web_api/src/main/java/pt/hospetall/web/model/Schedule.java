package pt.hospetall.web.model;

import pt.hospetall.web.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class Schedule extends BaseEntity {


    @OneToOne(mappedBy = "owner")
    private Pet pet;

    @NotNull
    private Timestamp date;

    @NotEmpty
    //@Digits(integer=2, fraction=0)
    private Short type;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date){
        this.date = date;
    }

    public String getPet() {
        return pet.getName();
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }
}
