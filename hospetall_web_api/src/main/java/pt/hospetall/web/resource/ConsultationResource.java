package pt.hospetall.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.ConsultationController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.controller.VeterinarianController;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Veterinarian;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(collectionRelation = "consultations", value = "consultation")
public class ConsultationResource extends Resource<Consultation> {


	public ConsultationResource(Consultation content) {
		super(content, getLinks(content));
	}

	private static Link[] getLinks(Consultation consultation){
		return new Link[]{
				linkTo(methodOn(ConsultationController.class).get(consultation.getId())).withSelfRel(),
				linkTo(methodOn(PetController.class).get(consultation.getPet().getId())).withRel("pet"),
				linkTo(methodOn(VeterinarianController.class).get(consultation.getVeterinarian().getId()))
						.withRel("veterinarian")
		};
	}

	public static Resources<ConsultationResource> getConsultations(Collection<Consultation> consultationList, Link self){
		List<ConsultationResource> list = consultationList.stream()
				.map(ConsultationResource::new)
				.collect(Collectors.toList());
		return new Resources<>(list, self);
	}
}
