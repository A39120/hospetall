package pt.hospetall.web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.shift.NurseShift;

@RepositoryRestResource(collectionResourceRel = "shifts", path="shift")
public interface INurseShiftRepository extends IShiftRepository<NurseShift> { }
