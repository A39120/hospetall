package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Treatment;
import pt.hospetall.web.repository.IClientRepository;
import pt.hospetall.web.resource.ClientResource;
import pt.hospetall.web.resource.ConsultationResource;
import pt.hospetall.web.resource.PetResource;
import pt.hospetall.web.resource.TreatmentResource;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(path = "/client")
public class ClientController extends AbstractGenericController<Client, IClientRepository, ClientResource> {

	@Autowired
	public ClientController(IClientRepository clientRepository) {
		super(clientRepository, ClientController.class);
	}

	@RequestMapping(path = "/{id}/pets", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json+hal"})
	public Resources<PetResource> getPets(@PathVariable int id) {
		Optional<Client> client = repo.findById(id);

		if (client.isPresent()) {
			Link self = linkTo(methodOn(ClientController.class).getPets(id)).withSelfRel();
			return PetResource.getResources(client.get().getPets(), self);
		}

		throw new RuntimeException();
	}


	@GetMapping(path = "/{id}/pet/consultation", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json+hal"})
	public Resources<ConsultationResource> getConsultations(@PathVariable int id){
		List<Consultation> consultations = this.getPets(id)
				.getContent()
				.stream()
				.map(Resource::getContent)
				.flatMap(p -> p.getConsultations().stream())
				.collect(Collectors.toList());

		Link link = linkTo(methodOn(ClientController.class).getConsultations(id)).withSelfRel();
		return ConsultationResource.getConsultations(consultations, link);
	}


	@GetMapping(path = "/{id}/pet/treatment", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json+hal"})
	public Resources<TreatmentResource> getTreatment(@PathVariable int id){
		List<Treatment> treatment = this.getPets(id)
				.getContent()
				.stream()
				.map(Resource::getContent)
				.flatMap(p -> p.getTreatments().stream())
				.collect(Collectors.toList());

		Link link = linkTo(methodOn(ClientController.class).getConsultations(id)).withSelfRel();
		return TreatmentResource.getTreatments(treatment, link);
	}

	@GetMapping(path = "/{id}/pets/procedure", produces = {MediaType.APPLICATION_JSON_VALUE, "application/json+hal"})
	public ProcedureResource getProcedures(@PathVariable int id){

		Resources<TreatmentResource> treatmentResources = this.getTreatment(id);
		Resources<ConsultationResource> consultationResources = this.getConsultations(id);

		Link self = linkTo(methodOn(ClientController.class).getProcedures(id)).withSelfRel();
		return new ProcedureResource(treatmentResources, consultationResources, self);

	}

	private class ProcedureResource extends ResourceSupport {

		private final Resources<TreatmentResource> treatmentResources;
		private final Resources<ConsultationResource> consultationResources;

		private ProcedureResource(Resources<TreatmentResource> treatmentResources, Resources<ConsultationResource> consultationResources,
								  Link self) {
			this.treatmentResources = treatmentResources;
			this.consultationResources = consultationResources;
			this.add(self);
		}

		public Resources<TreatmentResource> getTreatmentResources() {
			return treatmentResources;
		}

		public Resources<ConsultationResource> getConsultationResources() {
			return consultationResources;
		}
	}


	@Override
	protected ClientResource getResource(Client obj) {
		return new ClientResource(obj);
	}

	@Override
	protected Resources<ClientResource> getResources(List<Client> obj, Link self) {
		List<ClientResource> clients = obj
				.stream()
				.map(ClientResource::new)
				.collect(Collectors.toList());

		return new Resources<>(clients, self);
	}


	@PostMapping()
	ResponseEntity<?> add(@RequestBody Client input) {

		return repo.findClientByNif(input.getNif())
				.map(account -> ResponseEntity
						.created(
								URI.create(
										new ClientResource(
												repo.save(input))
												.getLink("self").getHref()))
						.build())
				.orElse(ResponseEntity.noContent().build());
	}


}
