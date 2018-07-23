package pt.hospetall.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.Nurse;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Treatment;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "treatments", path="treatment")
public interface ITreatmentRepository extends IMedicalProcedureRepository<Treatment>{
	List<Treatment> findAllByNurseId(@Param("nurse") int id);
}
