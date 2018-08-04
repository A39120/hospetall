package pt.hospetall.web.model;

import pt.hospetall.web.model.base.Schedule;
import pt.hospetall.web.model.person.Veterinarian;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ConsultationSchedule")
public class ConsultationSchedule extends Schedule {

	@ManyToOne
	private Veterinarian veterinarian;

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}
}
