package pt.hospetall.web.repository;

import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Veterinarian;

import java.util.List;

public interface IConsultationRepository extends IMedicalProcedureRepository<Consultation> {

	List<Consultation> findAllByVeterinarian(Veterinarian veterinarian);
	List<Consultation> findAllByPet(Pet pet);

}
