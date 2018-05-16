package pt.hospetall.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.repository.IPetRepository;
import pt.hospetall.web.resource.ConsultationResource;
import pt.hospetall.web.resource.PetResource;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/pet")
public class PetController extends AbstractGenericController<Pet, IPetRepository, PetResource> {

	public PetController(IPetRepository petRepository) {
		super(petRepository, PetController.class);
	}

	@RequestMapping(method = RequestMethod.GET,
			path = "/{id}/consultation",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<ConsultationResource> getPetConsultations(@PathVariable int id) {
		Set<Consultation> consultations = this
				.get(id)
				.getContent()
				.getConsultations();

		Link self = linkTo(methodOn(PetController.class).getPetConsultations(id)).withSelfRel();
		return ConsultationResource.getConsultations(consultations, self);
	}

	@Override
	protected PetResource getResource(Pet obj) {
		return new PetResource(obj);
	}

	@Override
	protected Resources<PetResource> getResources(List<Pet> obj, Link self) {
		return PetResource.getResources(obj, self);
	}
}
