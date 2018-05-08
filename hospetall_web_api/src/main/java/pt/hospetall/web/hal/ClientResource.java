package pt.hospetall.web.hal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.controller.ClientController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

	public Client getClient() { return this.client; }

	public static Resources<ClientResource> getClients(List<Client> clients, Link self){

		List<ClientResource> resource = clients
				.stream()
				.map(ClientResource::new)
				.collect(Collectors.toList());

		return new Resources<>(resource, self);

	}

}
