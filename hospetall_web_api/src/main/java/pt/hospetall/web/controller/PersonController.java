package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.PersonResource;
import pt.hospetall.web.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

	private final PersonRepository personPersonRepository;

	@Autowired
	public PersonController(PersonRepository personPersonRepository){
		this.personPersonRepository = personPersonRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<PersonResource>> getPersonList(){

		List<PersonResource> res = personPersonRepository
				.findAll()
				.stream()
				.map(PersonResource::new)
				.collect(Collectors.toList());

		Resources<PersonResource> resources = new Resources<>(res);
		return ResponseEntity.ok(resources);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<PersonResource> getPerson(@PathVariable int id){
		PersonResource person = new PersonResource(personPersonRepository.findById(id).get());
		//TODO: Case there is no person
		return ResponseEntity.ok(person);
	}
}
