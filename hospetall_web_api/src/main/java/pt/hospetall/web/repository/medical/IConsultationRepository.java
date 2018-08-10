package pt.hospetall.web.repository.medical;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import pt.hospetall.web.model.medical.Consultation;
import pt.hospetall.web.repository.base.IMedicalProcedureRepository;

import java.util.List;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VETERINARIAN')")
@RepositoryRestResource(collectionResourceRel = "consultations", path="consultation")
public interface IConsultationRepository extends IMedicalProcedureRepository<Consultation> {
	List<Consultation> findAllByVeterinarianId(@Param("veterinarian") int veterinarian);
}
