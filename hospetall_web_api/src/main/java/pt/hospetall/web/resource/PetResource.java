package pt.hospetall.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import pt.hospetall.web.controller.ClientController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.model.Pet;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PetResource extends Resource<Pet> {

	public PetResource(Pet content) {
		super(content, getLinks(content));
	}

	private static Link[] getLinks(Pet pet){
		return new Link[]{
				linkTo(methodOn(PetController.class).get(pet.getId())).withSelfRel(),
				linkTo(methodOn(ClientController.class).get(pet.getOwner().getId())).withRel("owner"),
				linkTo(methodOn(PetController.class).getPetConsultations(pet.getId())).withRel("consultations")
		};
	}

	public static Resources<PetResource> getResources(Collection<Pet> obj, Link self) {
		List<PetResource> list = obj
				.stream()
				.map(PetResource::new)
				.collect(Collectors.toList());

		return new Resources<>(list, self);
	}
}
