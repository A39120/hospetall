package pt.hospetall.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.Consultation;
import pt.hospetall.web.model.Pet;
import pt.hospetall.web.model.Veterinarian;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "consultations", path="consultation")
public interface IConsultationRepository extends IMedicalProcedureRepository<Consultation> {
	List<Consultation> findAllByVeterinarianId(@Param("veterinarian") int veterinarian);
}
