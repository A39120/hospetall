package pt.hospetall.web.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;
import pt.hospetall.web.controller.NurseController;
import pt.hospetall.web.controller.TreatmentController;
import pt.hospetall.web.controller.PetController;
import pt.hospetall.web.model.Treatment;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Relation(collectionRelation = "treatments", value = "treatment")
public class TreatmentResource extends Resource<Treatment> {


	public TreatmentResource(Treatment content) {
		super(content, getLinks(content));
	}

	private static Link[] getLinks(Treatment treatment){
		return new Link[]{
				linkTo(methodOn(TreatmentController.class).get(treatment.getId())).withSelfRel(),
				linkTo(methodOn(PetController.class).get(treatment.getPet().getId())).withRel("pet"),
				linkTo(methodOn(NurseController.class).get(treatment.getNurse().getId()))
						.withRel("nurse")
		};
	}

	public static Resources<TreatmentResource> getTreatments(Collection<Treatment> treatmentList, Link self){
		List<TreatmentResource> list = treatmentList.stream()
				.map(TreatmentResource::new)
				.collect(Collectors.toList());
		return new Resources<>(list, self);
	}
}

