package pt.hospetall.web.hal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.ClientController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.model.Pet;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Relation(value="pet", collectionRelation = "pets")
public class PetResource extends ResourceSupport {

	private final Pet pet;

	public PetResource(Pet pet){
		this.pet = pet;
		addLinkToSelf();
	}

	void addLinkToSelf(){
		int id = pet.getId();
		Link self = linkTo(methodOn(PetController.class).getPet(id)).withSelfRel();
		this.add(self);
	}

	public void addLinkToOwner(){
		int owner = pet.getOwner().getId();
		this.add(linkTo(methodOn(ClientController.class).getClient(owner)).withRel("owner"));
	}

	public void addLinkToRace(){
		int race = pet.getRace().getId();
		//TODO:
	}

	public void addLinkToSpecies(){
		int species = pet.getSpecies().getId();
		//TODO:
	}

	public void addLinkToConsultations(){
		this.add(linkTo(methodOn(PetController.class).getPetConsultations(pet.getId())).withRel("consultations"));
	}

	public Pet getPet(){ return pet; }

}
