package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.repository.IConsultationRepository;
import pt.hospetall.web.resource.ConsultationResource;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path="/consultation")
public class ConsultationController extends AbstractGenericController<Consultation,
		IConsultationRepository,
		ConsultationResource> {

	@Autowired
	public ConsultationController(IConsultationRepository consultationRepository) {
		super(consultationRepository, ConsultationController.class);
	}

	@Override
	protected ConsultationResource getResource(Consultation obj) {
		return new ConsultationResource(obj);
	}

	@Override
	protected Resources<ConsultationResource> getResources(List<Consultation> obj, Link self) {
		return ConsultationResource.getConsultations(obj, self);
	}

	//@Override
	public Optional<Consultation> checkIfExists(Consultation entity) {
		return null;
	}
}
