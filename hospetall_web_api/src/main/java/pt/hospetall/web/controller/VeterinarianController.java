package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.hal.VeterinarianResource;
import pt.hospetall.web.repository.IVeterinarianRepository;

@RestController
@RequestMapping(path="/vet")
public class VeterinarianController {

	private final IVeterinarianRepository veterinarianRepository;

	@Autowired
	public VeterinarianController(IVeterinarianRepository veterinarianRepository) {
		this.veterinarianRepository = veterinarianRepository;
	}

	@RequestMapping(method = RequestMethod.GET,
			path = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, "application/json+hal"})
	public ResponseEntity<VeterinarianResource> getVet(@PathVariable int id){
		VeterinarianResource vr = veterinarianRepository
				.findById(id)
				.map(VeterinarianResource::new)
				.get();

		return ResponseEntity.ok(vr);
	}

}
