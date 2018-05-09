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
import pt.hospetall.web.hal.NurseResource;
import pt.hospetall.web.repository.INurseRepository;
import pt.hospetall.web.repository.INurseRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/nurse")
public class NurseController {

	private final INurseRepository nurseRepository;

	@Autowired
	public NurseController(INurseRepository nurseRepository) {
		this.nurseRepository = nurseRepository;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public NurseResource getNurse(@PathVariable int id){
		return new NurseResource(nurseRepository.findById(id).get());
	}

	@RequestMapping(method = RequestMethod.GET,  produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	ResponseEntity<Resources<NurseResource>> getNurses(){

		List<NurseResource> nurses = nurseRepository
				.findAll()
				.stream()
				.map(NurseResource::new)
				.collect(Collectors.toList());
		Link self = linkTo(methodOn(NurseController.class).getNurses()).withSelfRel();
		Resources<NurseResource> resources = new Resources<>(nurses);
		resources.add(self);

		return ResponseEntity.ok(resources);
	}
}
