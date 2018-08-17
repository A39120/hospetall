package pt.hospetall.web.model.shift;

import pt.hospetall.web.error.exceptions.InvalidWorkTimeException;
import pt.hospetall.web.model.base.Period;
import pt.hospetall.web.util.CalendarUtil;
import javax.persistence.MappedSuperclass;
import java.util.*;

import static java.util.Calendar.MINUTE;

@MappedSuperclass
public abstract class Shift extends Period{

	private boolean weekly;

	public boolean isWeekly() {
		return weekly;
	}

	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	// Util methods used for shift

	public boolean inside(Shift shift){
		if(shift.isWeekly() == this.isWeekly())
			return super.inside(shift);

		else {
			Calendar thisCalendar = parseStartToCalendar();
			Calendar otherCalendar = shift.parseStartToCalendar();
			if(!CalendarUtil.weekDayIsSame(thisCalendar, otherCalendar))
				return false;

			if(!CalendarUtil.timeIsAfter(thisCalendar, otherCalendar))
				return false;

			thisCalendar.add(MINUTE, this.getMinutes());
			otherCalendar.add(MINUTE, shift.getMinutes());
			return CalendarUtil.timeIsAfter(otherCalendar, thisCalendar);
		}
	}

	public boolean intersect(Shift shift){
		if(shift.isWeekly() == this.isWeekly()){
			return !super.intersect(shift);
		} else {
			Calendar thisCalendar = parseStartToCalendar();
			Calendar otherCalendar = shift.parseStartToCalendar();

			if(!CalendarUtil.weekDayIsSame(thisCalendar, otherCalendar))
				return false;

			if(CalendarUtil.timeIsAfter(otherCalendar, thisCalendar)){
				thisCalendar.add(MINUTE, this.getMinutes());
				return CalendarUtil.timeIsAfter(thisCalendar, otherCalendar);
			}

			otherCalendar.add(MINUTE, this.getMinutes());
			return CalendarUtil.timeIsAfter(otherCalendar, thisCalendar);
		}
	}

	public void interjoin(Shift shift){
		if(!intersect(shift))
			return;

		//Both are same, join them
		if(this.isWeekly() == shift.isWeekly())
			super.interjoin(shift);

		else if(!this.isWeekly()){
			Calendar thisCalendar = this.parseStartToCalendar();
			Calendar otherCalendar = shift.parseStartToCalendar();

			if(CalendarUtil.timeIsAfter(thisCalendar, otherCalendar)){
				otherCalendar.add(MINUTE, shift.getMinutes());
				if(!CalendarUtil.weekDayIsSame(thisCalendar, otherCalendar))
					throw new InvalidWorkTimeException();

				long oldStart = this.getStartPeriod();
				thisCalendar.set(Calendar.HOUR_OF_DAY, otherCalendar.get(Calendar.HOUR_OF_DAY));
				thisCalendar.set(MINUTE, otherCalendar.get(MINUTE));
				thisCalendar.set(Calendar.SECOND, otherCalendar.get(Calendar.SECOND));
				thisCalendar.set(Calendar.MILLISECOND, otherCalendar.get(Calendar.MILLISECOND));
				this.setStartPeriod(thisCalendar.getTimeInMillis());

				thisCalendar.setTimeInMillis(oldStart);
				thisCalendar.add(MINUTE, getMinutes());
				int hours = thisCalendar.get(Calendar.HOUR_OF_DAY) - otherCalendar.get(Calendar.HOUR_OF_DAY);
				this.setMinutes(hours * 60 + (thisCalendar.get(MINUTE) - otherCalendar.get(MINUTE)));
			} else {
				if(!CalendarUtil.weekDayIsSame(thisCalendar, otherCalendar))
					throw new InvalidWorkTimeException();

				int hours = otherCalendar.get(Calendar.HOUR_OF_DAY) - thisCalendar.get(Calendar.HOUR_OF_DAY);
				this.setMinutes(hours * 60 + (thisCalendar.get(MINUTE) - otherCalendar.get(MINUTE)));
			}
		}
	}


}
