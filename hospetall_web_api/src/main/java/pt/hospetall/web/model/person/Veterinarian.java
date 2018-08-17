package pt.hospetall.web.model.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.medical.Consultation;
import pt.hospetall.web.model.person.base.Person;
import pt.hospetall.web.model.schedule.ConsultationSchedule;
import pt.hospetall.web.model.shift.VeterinarianShift;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Veterinarian extends Person {

	@JsonIgnore
	@OneToMany(mappedBy = "veterinarian", targetEntity = VeterinarianShift.class)
	private Set<VeterinarianShift> shifts;

	@JsonIgnore
	@OneToMany(mappedBy = "veterinarian", targetEntity = Consultation.class)
	private Set<Consultation> consultations;

	@JsonIgnore
	@OneToMany(mappedBy = "veterinarian", targetEntity = ConsultationSchedule.class)
	private Set<ConsultationSchedule> schedules;

	public Set<VeterinarianShift> getShifts() {
		return shifts;
	}

	public void setShifts(Set<VeterinarianShift> shifts) {
		this.shifts = shifts;
	}

	public Set<Consultation> getConsultations() {
		return consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	public Set<ConsultationSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Set<ConsultationSchedule> schedules) {
		this.schedules = schedules;
	}
}
