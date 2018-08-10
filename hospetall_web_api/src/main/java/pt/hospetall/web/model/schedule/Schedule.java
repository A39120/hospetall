package pt.hospetall.web.model.schedule;

import pt.hospetall.web.model.person.Client;
import pt.hospetall.web.model.base.Period;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Schedule extends Period {

	@ManyToOne
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
