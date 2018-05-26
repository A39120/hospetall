package pt.hospetall.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.ClientController;
import pt.hospetall.web.model.Client;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(value = "client", collectionRelation = "client")
public class ClientResource extends Resource<Client> {

	//private final Client client;

	public ClientResource(Client client){
		super(client, getLinks(client));
	//	this.client = client;
	//	this.add(getLinks(client));
	}

	private static List<Link> getLinks(Client client){
		LinkedList<Link> list = new LinkedList<>();
		list.add(linkTo(methodOn(ClientController.class).get(client.getId())).withSelfRel());
		list.add(linkTo(methodOn(ClientController.class).getPets(client.getId())).withRel("pets"));
		return list;
	}

}
