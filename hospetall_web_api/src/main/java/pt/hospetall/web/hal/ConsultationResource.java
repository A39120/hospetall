package pt.hospetall.web.hal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.ConsultationController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.controller.VeterinarianController;
import pt.hospetall.web.model.Consultation;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(value = "consultation", collectionRelation = "consultations")
public class ConsultationResource extends ResourceSupport {

	private final Consultation consultation;

	@Autowired
	public ConsultationResource(Consultation consultation) {
		this.consultation = consultation;
		addLinkToSelf();
	}

	public Consultation getConsultation() {
		return consultation;
	}

	public void addLinkToSelf(){
		int id = consultation.getId();
		this.add(linkTo(methodOn(ConsultationController.class).getConsultation(id)).withSelfRel());
	}

	public void addLinkToPet(){
		int petId = consultation.getPet().getId();
		this.add(linkTo(methodOn(PetController.class).getPet(petId)).withRel("pet"));
	}

	public void addLinkToVeterinarian(){
		int vetId = consultation.getVeterinarian().getId();
		this.add(linkTo(methodOn(VeterinarianController.class).getVet(vetId)).withRel("veterinarian"));
	}

}
