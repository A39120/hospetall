package pt.hospetall.web.hal;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import pt.hospetall.web.controller.VeterinarianController;
import pt.hospetall.web.model.Veterinarian;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class VeterinarianResource extends ResourceSupport {

	private final Veterinarian veterinarian;

	public VeterinarianResource(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
		Link link = linkTo(methodOn(VeterinarianController.class).getVet(veterinarian.getId())).withSelfRel();
		this.add(link);
	}

	public void addLinkToConsultations(){
		//TODO: Controller method
	}

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}
}
