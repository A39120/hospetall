package pt.hospetall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.error.exceptions.ScheduleNotFoundException;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.repository.person.INurseRepository;
import pt.hospetall.web.repository.schedule.ITreatmentScheduleRepository;

@RepositoryRestController
@RequestMapping
public class AlterTreatmentScheduleController {

	private final INurseRepository nurseRepository;
	private final ITreatmentScheduleRepository treatmentScheduleRepository;

	@Autowired
	public AlterTreatmentScheduleController(INurseRepository nurseRepository,
											ITreatmentScheduleRepository treatmentScheduleRepository) {

		this.nurseRepository = nurseRepository;
		this.treatmentScheduleRepository = treatmentScheduleRepository;
	}

	@PutMapping(path = "/alter/treatment_schedule/{id}", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity alterTreatmentSchedule(
			@RequestBody TreatmentSchedule treatmentSchedule,
			@PathVariable(name = "id") int id,
			@RequestParam(name = "nurse", required = false) int nurse_id) throws ScheduleNotFoundException, PersonNotFoundException{

		TreatmentSchedule toAlter = treatmentScheduleRepository.findById(id)
				.orElseThrow(ScheduleNotFoundException::new);

		treatmentSchedule.setId(id);
		Nurse nurse = toAlter.getNurse();
		if(nurse_id > 0)
			nurse = nurseRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		treatmentSchedule.setNurse(nurse);
		TreatmentSchedule altered = treatmentScheduleRepository.save(treatmentSchedule);
		return ResponseEntity.ok(altered);
	}

}
