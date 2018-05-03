package pt.hospetall.web.person;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

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
	 	this.add(linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel());
	 }
}
