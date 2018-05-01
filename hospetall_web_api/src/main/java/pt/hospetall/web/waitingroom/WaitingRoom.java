package pt.hospetall.web.waitingroom;

import pt.hospetall.web.person.client.Client;
import pt.hospetall.web.pet.Pet;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class WaitingRoom {

    @OneToMany(mappedBy = "waitingRoom")
    private Set<Pet> pets = new HashSet<>();
}
