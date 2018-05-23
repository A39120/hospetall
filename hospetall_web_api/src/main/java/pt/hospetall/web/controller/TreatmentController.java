package pt.hospetall.web.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Treatment;
import pt.hospetall.web.repository.ITreatmentRepository;
import pt.hospetall.web.resource.TreatmentResource;

import java.util.List;
import java.util.Optional;

@RestController
public class TreatmentController extends AbstractGenericController<Treatment, ITreatmentRepository, TreatmentResource> {

	protected TreatmentController(ITreatmentRepository repo) {
		super(repo, TreatmentController.class);
	}

	@Override
	protected TreatmentResource getResource(Treatment obj) {
		return new TreatmentResource(obj);
	}

	@Override
	protected Resources<TreatmentResource> getResources(List<Treatment> obj, Link self) {
		return TreatmentResource.getTreatments(obj, self);
	}

	@Override
	public Optional<Treatment> checkIfExists(Treatment entity) {
		return Optional.empty();
	}
}
