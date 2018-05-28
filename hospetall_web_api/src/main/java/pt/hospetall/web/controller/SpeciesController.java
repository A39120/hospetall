package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Species;
import pt.hospetall.web.repository.ISpeciesRepository;

import java.util.Optional;


@RestController
@RequestMapping(path = "/species")
public class SpeciesController extends AbstractGenericController<Species, ISpeciesRepository, Resource<Species>> {

	@Autowired
	protected SpeciesController(ISpeciesRepository repo) {
		super(repo, SpeciesController.class);
	}

	//@Override
	public Optional<Species> checkIfExists(Species entity) {
		return Optional.empty();
	}
}
