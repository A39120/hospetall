package pt.hospetall.web.hal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.ClientController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.controller.RaceController;
import pt.hospetall.web.controller.SpeciesController;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Race;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Relation(value="pet", collectionRelation = "pets")
public class PetResource extends ResourceSupport {

	private final Pet pet;
	private final String race;
	private final String species;

	public PetResource(Pet pet){
		this.pet = pet;
		this.race = pet.getRace().getName();
		this.species = pet.getSpecies().getName();
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

	public void addLinkToRace() throws Exception {
		Race race = pet.getRace();

		if(race != null) {
			int raceId = race.getId();
			this.add(linkTo(methodOn(RaceController.class).get(raceId)).withRel("race"));
		}
	}

	public void addLinkToSpecies() throws Exception {
		int species = pet.getSpecies().getId();
		this.add(linkTo(methodOn(SpeciesController.class).get(species)).withRel("species"));
	}

	public void addLinkToConsultations(){
		this.add(linkTo(methodOn(PetController.class).getPetConsultations(pet.getId())).withRel("consultations"));
	}

	public Pet getPet(){ return pet; }

	public void addLinks(){
		addLinkToOwner();
		addLinkToConsultations();
		try {
			addLinkToRace();
			addLinkToSpecies();
		} catch (Exception ignored) { }
	}
}
