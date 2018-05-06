package pt.hospetall.web.hal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.controller.PersonController;
import pt.hospetall.web.controller.ClientController;


@Relation(collectionRelation = "client", value = "client")
public class ClientResource extends ResourceSupport {

	private final Client client;

	public ClientResource(Client client){
		this.client = client;
		this.addLinkToSelf();
	}

	public void addLinkToSelf(){
		this.add(ControllerLinkBuilder.linkTo(methodOn(ClientController.class).getClient(client.getId())).withSelfRel());
	}

	public void addLinkToPerson(){
		int personId = client.getPerson().getId();
		this.add(linkTo(methodOn(PersonController.class).getPerson(personId)).withRel("person"));
	}

	public Client getClient() { return this.client; }

}
