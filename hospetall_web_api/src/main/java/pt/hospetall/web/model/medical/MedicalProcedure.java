package pt.hospetall.web.model.medical;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.base.BaseEntity;
import pt.hospetall.web.model.pet.Pet;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class MedicalProcedure extends BaseEntity {

	@NotNull
	@JsonIgnore
	@ManyToOne
	private Pet pet;

	private String caseHistory, diagnosis, treatment, observations;

	private Timestamp date;

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date){
		this.date = date;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getCaseHistory() {
		return caseHistory;
	}

	public void setCaseHistory(String caseHistory) {
		this.caseHistory = caseHistory;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

}
