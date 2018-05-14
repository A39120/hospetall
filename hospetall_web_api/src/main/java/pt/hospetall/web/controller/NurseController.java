package pt.hospetall.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Nurse;
import pt.hospetall.web.repository.INurseRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/nurse")
public class NurseController extends AbstractGenericController<Nurse, INurseRepository> {

	protected NurseController(INurseRepository repo) {
		super(repo, NurseController.class);
	}

	@Override
	protected Link[] getLinks(int id) {
		return new Link[]{

		};
	}
}
