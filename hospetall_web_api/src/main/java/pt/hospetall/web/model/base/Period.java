package pt.hospetall.web.model.base;

import javax.persistence.MappedSuperclass;

/**
 * Entity responsible for time periods
 */
@MappedSuperclass
public abstract class Period extends BaseEntity {

	/**
	 * start_period represents the start_period of a period
	 * end_period represents the expected end_period of a period
	 */
	private long start_period;
	private long end_period;

	public long getEnd_period() {
		return end_period;
	}

	public void setEnd_period(long end_period) {
		this.end_period = end_period;
	}

	public long getStart_period() {
		return start_period;
	}

	public void setStart_period(long start_period) {
		this.start_period = start_period;
	}
}
