package pt.hospetall.web.hal;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import pt.hospetall.web.controller.NurseController;
import pt.hospetall.web.model.Nurse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Relation(collectionRelation = "nurse", value = "nurse")
public class NurseResource extends ResourceSupport {

	private final Nurse nurse;

	public NurseResource(Nurse nurse){
		this.nurse = nurse;
		this.addLinkToSelf();
	}

	public void addLinkToSelf(){
		this.add(ControllerLinkBuilder.linkTo(methodOn(NurseController.class).getNurse(nurse.getId())).withSelfRel());
	}

	public Nurse getNurse() { return this.nurse; }

}
