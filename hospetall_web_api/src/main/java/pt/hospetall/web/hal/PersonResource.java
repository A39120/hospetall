package pt.hospetall.web.hal;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import pt.hospetall.web.model.Person;
import pt.hospetall.web.controller.PersonController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(collectionRelation = "people", value = "person")
public class PersonResource extends ResourceSupport {

	private final Person person;

	 public PersonResource(Person person) {
		this.person = person;
		setSelfLink();
	 }

	 public void setSelfLink(){
	 	this.add(ControllerLinkBuilder.linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel());
	 }
}
