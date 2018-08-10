package pt.hospetall.web.repository.medical;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.medical.Treatment;
import pt.hospetall.web.repository.base.IMedicalProcedureRepository;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "treatments", path="treatment")
public interface ITreatmentRepository extends IMedicalProcedureRepository<Treatment> {
	List<Treatment> findAllByNurseId(@Param("nurse") int id);
}
