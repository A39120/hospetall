package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.ClientResource;
import pt.hospetall.web.repository.IClientRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/client")
public class ClientController {

	private final IClientRepository clientRepository;

	@Autowired
	public ClientController(IClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ClientResource getClient(@PathVariable int id){
		return new ClientResource(clientRepository.findById(id).get());
	}

	@RequestMapping(method = RequestMethod.GET,  produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	ResponseEntity<Resources<ClientResource>> getClients(){

		List<ClientResource> clients = clientRepository
				.findAll()
				.stream()
				.map(ClientResource::new)
				.collect(Collectors.toList());
		Link self = linkTo(methodOn(ClientController.class).getClients()).withSelfRel();
		Resources<ClientResource> resources = new Resources<>(clients);
		resources.add(self);

		return ResponseEntity.ok(resources);
	}
}
