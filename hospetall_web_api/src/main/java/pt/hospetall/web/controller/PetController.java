package pt.hospetall.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Treatment;
import pt.hospetall.web.repository.IPetRepository;
import pt.hospetall.web.resource.ConsultationResource;
import pt.hospetall.web.resource.PetResource;
import pt.hospetall.web.resource.TreatmentResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/pet")
public class PetController extends AbstractGenericController<Pet, IPetRepository, PetResource> {

	public PetController(IPetRepository petRepository) {
		super(petRepository, PetController.class);
	}

	@GetMapping(path = "/{id}/consultation",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<ConsultationResource> getPetConsultations(@PathVariable int id) {
		Set<Consultation> consultations = this
				.get(id)
				.getContent()
				.getConsultations();

		Link self = linkTo(methodOn(PetController.class).getPetConsultations(id)).withSelfRel();
		return ConsultationResource.getConsultations(consultations, self);
	}

	@GetMapping(path = "/{id}/treatment",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public Resources<TreatmentResource> getPetTreatments(@PathVariable int id) {
		Set<Treatment> threatments = this
				.get(id)
				.getContent()
				.getTreatments();

		Link self = linkTo(methodOn(PetController.class).getPetTreatments(id)).withSelfRel();
		return TreatmentResource.getTreatments(threatments, self);
	}

	@Override
	protected PetResource getResource(Pet obj) {
		return new PetResource(obj);
	}

	@Override
	protected Resources<PetResource> getResources(List<Pet> obj, Link self) {
		return PetResource.getResources(obj, self);
	}

	@Override
	public Optional<Pet> checkIfExists(Pet entity) {
		return Optional.empty();
	}
}
