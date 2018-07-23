package pt.hospetall.web.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pt.hospetall.web.model.Client;
import pt.hospetall.web.model.base.BaseEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Schedule extends BaseEntity {

	private long startDate;
	private long period;

	@JsonIgnore
	@ManyToOne
	private Client client;

	public long getPeriod() {
		return period;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
