package pt.hospetall.web.model.base;

import javax.persistence.MappedSuperclass;
import java.util.Calendar;

/**
 * Entity responsible for time periods
 */
@MappedSuperclass
public abstract class Period extends BaseEntity implements Comparable<Period>{

	/**
	 * startPeriod represents the startPeriod of a period
	 * end_period represents the expected end_period of a period
	 */
	private long startPeriod;
	private int minutes;

	public long getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(long startPeriod) {
		this.startPeriod = startPeriod;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public boolean inside(Period period){
		return getStartPeriod() > period.getStartPeriod() && calculateEnd() < period.calculateEnd();
	}

	public boolean intersect(Period period){
		return (this.getStartPeriod() >= period.getStartPeriod() ||
				this.calculateEnd() <= period.getStartPeriod()) &&
				(period.getStartPeriod() >= this.getStartPeriod() ||
						period.calculateEnd() <= this.getStartPeriod());
	}

	public void interjoin(Period period){
		if(intersect(period))
			return;

		//Both are same, join them
		this.setStartPeriod(this.getStartPeriod() <= period.getStartPeriod() ? this.getStartPeriod() : period.getStartPeriod());
		long end0 = this.getStartPeriod() + this.getMinutes();
		long end1 = period.getStartPeriod() + period.getMinutes();
		this.setMinutes((int)((end0 >= end1 ? end0 : end1) - getStartPeriod()));
	}

	public Calendar parseStartToCalendar(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.getStartPeriod());
		return calendar;
	}

	@Override
	public int compareTo(Period o) {
		int first = (int)(getStartPeriod() - o.getStartPeriod());
		if(first != 0)
			return first;
		else
			return (int)(getMinutes() - o.getMinutes());
	}

	public long calculateEnd(){
		/*
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startPeriod);
		calendar.add(Calendar.MINUTE, minutes);
		*/

		long minutes = 60 * 1000 * getMinutes();
		return startPeriod + minutes;
	}
}
