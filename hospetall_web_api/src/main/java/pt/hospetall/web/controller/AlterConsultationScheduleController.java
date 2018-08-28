package pt.hospetall.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.hospetall.web.error.exceptions.PersonNotFoundException;
import pt.hospetall.web.error.exceptions.ScheduleNotFoundException;
import pt.hospetall.web.model.person.Veterinarian;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.repository.person.IVeterinarianRepository;
import pt.hospetall.web.repository.schedule.IConsultationScheduleRepository;

@RepositoryRestController
@RequestMapping
public class AlterConsultationScheduleController {

	private final IVeterinarianRepository veterinarianRepository;

	private final IConsultationScheduleRepository consultationScheduleRepository;

	@Autowired
	public AlterConsultationScheduleController(IVeterinarianRepository veterinarianRepository, IConsultationScheduleRepository consultationScheduleRepository) {
		this.veterinarianRepository = veterinarianRepository;
		this.consultationScheduleRepository = consultationScheduleRepository;
	}

	@PutMapping(path = "/alter/consultation_schedule/{id}", produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity alterConsultationSchedule(
			@RequestBody ConsultationSchedule consultationSchedule,
			@PathVariable(name = "id") int id,
			@RequestParam(name = "veterinarian", required = false) int veterinarian_id) throws ScheduleNotFoundException, PersonNotFoundException {

		ConsultationSchedule toAlter = consultationScheduleRepository.findById(id)
				.orElseThrow(ScheduleNotFoundException::new);

		consultationSchedule.setId(id);
		Veterinarian veterinarian = toAlter.getVeterinarian();
		if(veterinarian_id > 0)
			veterinarian = veterinarianRepository.findById(id).orElseThrow(PersonNotFoundException::new);

		consultationSchedule.setVeterinarian(veterinarian);
		ConsultationSchedule altered = consultationScheduleRepository.save(consultationSchedule);
		return ResponseEntity.ok(altered);
	}
}
