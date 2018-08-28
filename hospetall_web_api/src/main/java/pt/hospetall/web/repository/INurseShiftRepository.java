package pt.hospetall.web.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pt.hospetall.web.model.shift.NurseShift;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "nurse_shifts", path="nurse_shift")
public interface INurseShiftRepository extends IShiftRepository<NurseShift> { }
