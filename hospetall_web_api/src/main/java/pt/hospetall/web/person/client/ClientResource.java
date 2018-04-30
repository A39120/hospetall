package pt.hospetall.web.person.client;

import org.springframework.hateoas.ResourceSupport;

public class ClientResource extends ResourceSupport {

    private final Client client;


    public ClientResource(Client client) {
        String id = String.valueOf(client.getId());
        this.client = client;
    }



}
