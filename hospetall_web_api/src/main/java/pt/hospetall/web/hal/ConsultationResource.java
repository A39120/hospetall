package pt.hospetall.web.hal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import pt.hospetall.web.model.Consultation;

public class ConsultationResource extends ResourceSupport {

	private final Consultation consultation;

	@Autowired
	public ConsultationResource(Consultation consultation) {
		this.consultation = consultation;
	}

	public Consultation getConsultation() {
		return consultation;
	}
}
