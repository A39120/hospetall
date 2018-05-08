package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.ConsultationResource;
import pt.hospetall.web.repository.IConsultationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path="/procedure/consultation")
public class ConsultationController {


	private final IConsultationRepository consultationRepository;

	@Autowired
	public ConsultationController(IConsultationRepository consultationRepository) {
		this.consultationRepository = consultationRepository;
	}

	@RequestMapping(method = RequestMethod.GET, path="", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<Resources<ConsultationResource>> getConsultations(){
		List<ConsultationResource> consultations = consultationRepository
				.findAll()
				.stream()
				.map(ConsultationResource::new)
				.collect(Collectors.toList());
		Resources<ConsultationResource> res = new Resources<>(consultations);
		res.add(linkTo(methodOn(ConsultationController.class).getConsultations()).withSelfRel());
		return ResponseEntity.ok(res);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	public ResponseEntity<ConsultationResource> getConsultation(@PathVariable int id){
		Optional<ConsultationResource> consultation = consultationRepository
				.findById(id)
				.map(c -> {
					ConsultationResource cr = new ConsultationResource(c);
					cr.addLinkToPet();
					cr.addLinkToVeterinarian();
					return cr;
				});

		return ResponseEntity.ok(consultation.get());
	}
}
