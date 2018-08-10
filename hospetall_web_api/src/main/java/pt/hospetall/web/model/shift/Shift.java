package pt.hospetall.web.model.shift;

import pt.hospetall.web.model.base.Period;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Shift extends Period {

	private boolean weekly;

	public boolean isWeekly() {
		return weekly;
	}

	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}
}
