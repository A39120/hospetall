package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Race;
import pt.hospetall.web.repository.IRaceRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/race")
public class RaceController extends AbstractGenericController<Race, IRaceRepository> {

	@Autowired
	public RaceController(IRaceRepository rep) {
		super(rep, RaceController.class);
	}

	@RequestMapping(method = RequestMethod.GET,
			path = "/{id}/pet",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	ResponseEntity<Resource<Race>> getPetsWithRace(@PathVariable int id){
		//TODO: Maybe do this
		return null;
	}

	@Override
	protected Link[] getLinks(int id) {
		return new Link[]{
			linkTo(methodOn(RaceController.class).getPetsWithRace(id)).withRel("animals")
		};
	}
}
