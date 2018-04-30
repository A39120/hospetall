package pt.hospetall.web.waitingroom;

import pt.hospetall.web.client.Client;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class WaitingRoom {


    @OneToMany(mappedBy = "waitingRoom")
    private Set<Client> clients = new HashSet<>();
}
