package pt.hospetall.web.person.client;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.person.PersonController;


@Relation(collectionRelation = "client", value = "client")
public class ClientResource extends ResourceSupport {

	private final Client client;

	ClientResource(Client client){
		this.client = client;
		this.addLinkToSelf();
	}

	public void addLinkToSelf(){
		this.add(linkTo(methodOn(ClientController.class).getClient(client.getId())).withSelfRel());
	}

	public void addLinkToPerson(){
		int personId = client.getPerson().getId();
		this.add(linkTo(methodOn(PersonController.class).getPerson(personId)).withRel("person"));
	}

	public Client getClient() { return this.client; }

}
