package pt.hospetall.web.controller;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Nurse;
import pt.hospetall.web.repository.INurseRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/nurse")
public class NurseController extends AbstractGenericController<Nurse, INurseRepository, Resource<Nurse>> {

	protected NurseController(INurseRepository repo) {
		super(repo, NurseController.class);
	}

	//@Override
	public Optional<Nurse> checkIfExists(Nurse entity) {
		return Optional.empty();
	}
}
