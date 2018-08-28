package pt.hospetall.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.hospetall.web.model.person.Nurse;
import pt.hospetall.web.model.schedule.TreatmentSchedule;
import pt.hospetall.web.repository.INurseShiftRepository;
import pt.hospetall.web.repository.person.INurseRepository;

@Service
public class NurseScheduleService {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private INurseRepository nurseRepository;

	@Autowired
	private ShiftService shiftService;

	@Autowired
	private INurseShiftRepository nurseShiftRepository;

	public boolean addSchedule(){
		return false;
	}

	public boolean addSchedule(TreatmentSchedule treatmentSchedule){
		return false;
	}
}


