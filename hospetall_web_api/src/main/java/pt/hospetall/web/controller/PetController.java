package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.PetResource;
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
		Optional<Pet> pet = petRepository.findById(id);

		return ResponseEntity.ok(pet.map(PetResource::new).get());
	}
}
