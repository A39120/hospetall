package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.hospetall.web.controller.base.AbstractGenericController;
import pt.hospetall.web.model.Veterinarian;
import pt.hospetall.web.repository.IVeterinarianRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/veterinarian")
public class VeterinarianController extends AbstractGenericController<Veterinarian, IVeterinarianRepository, Resource<Veterinarian>> {

	@Autowired
	public VeterinarianController(IVeterinarianRepository veterinarianRepository) {
		super(veterinarianRepository, VeterinarianController.class);
	}

	//@Override
	public Optional<Veterinarian> checkIfExists(Veterinarian entity) {
		return repo.findByEmail(entity.getEmail());
	}
}
