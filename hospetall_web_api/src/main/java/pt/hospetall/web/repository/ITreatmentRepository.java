package pt.hospetall.web.repository;

import pt.hospetall.web.model.Nurse;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Treatment;

import java.util.List;

public interface ITreatmentRepository extends IMedicalProcedureRepository<Treatment> {

	List<Treatment> findAllByPet(Pet pet);
	List<Treatment> findAllByNurse(Nurse nurse);
}
