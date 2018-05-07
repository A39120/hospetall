package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.ConsultationResource;
import pt.hospetall.web.hal.PetResource;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.repository.IPetRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/pet")
public class PetController {

	private final IPetRepository petRepository;

	@Autowired
	public PetController(IPetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@RequestMapping(method = RequestMethod.GET, path="", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<Resources<PetResource>> getPets(){
		List<PetResource> pets = petRepository.findAll().stream().map(PetResource::new).collect(Collectors.toList());
		Resources<PetResource> res = new Resources<>(pets);
		res.add(linkTo(methodOn(PetController.class).getPets()).withSelfRel());
		return ResponseEntity.ok(res);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<PetResource> getPet(@PathVariable int id){
		PetResource pet = petRepository.findById(id).map(PetResource::new).get();
		pet.addLinkToConsultations();
		pet.addLinkToOwner();

		return ResponseEntity.ok(pet);
	}

	@RequestMapping(method = RequestMethod.GET,
			path = "/{id}/consultation",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<Resources<ConsultationResource>> getPetConsultations(@PathVariable int id){
		List<ConsultationResource> consultations = petRepository
				.findById(id)
				.get()
				.getConsultations()
				.stream()
				.map(c -> {
					ConsultationResource cr = new ConsultationResource(c);
					cr.addLinkToPet();
					cr.addLinkToVeterinarian();
					return cr;
				})
				.collect(Collectors.toList());

		Link self = linkTo(methodOn(PetController.class).getPetConsultations(id)).withSelfRel();

		return ResponseEntity.ok(new Resources<>(consultations, self));
	}
}
