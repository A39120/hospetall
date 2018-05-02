package pt.hospetall.web.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

	private final PersonRepository personPersonRepository;

	@Autowired
	public PersonController(PersonRepository<Person> personPersonRepository){
		this.personPersonRepository = personPersonRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Person> getPersonList(){
		Iterable<Person> res = personPersonRepository.findAll();
		return res;
	}
}
