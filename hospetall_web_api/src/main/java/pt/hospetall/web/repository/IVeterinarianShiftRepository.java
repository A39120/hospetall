package pt.hospetall.web.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.shift.VeterinarianShift;

@RepositoryRestResource(collectionResourceRel = "veterinarian_shifts", path="veterinarian_shift")
public interface IVeterinarianShiftRepository extends IShiftRepository<VeterinarianShift> {
}
