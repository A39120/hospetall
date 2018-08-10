package pt.hospetall.web.util;

import javafx.util.Pair;
import pt.hospetall.web.model.base.Period;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class CalendarPeriod {
	private final Calendar start;
	private final Calendar end;

	public CalendarPeriod(Calendar start, Calendar end){
		this.start = start;
		this.end = end;
	}

	public CalendarPeriod(long start, long end){
		this.start = this.end = Calendar.getInstance();
		this.start.setTimeInMillis(start);
		this.end.setTimeInMillis(end);
	}

	public CalendarPeriod(Period period){
		this.start = this.end = Calendar.getInstance();
		this.start.setTimeInMillis(period.getStart_period());
		this.end.setTimeInMillis(period.getEnd_period());
	}

	public Calendar getStart() { return start; }
	public Calendar getEnd() { return end; }

	public CalendarPeriod join(CalendarPeriod calendarPeriod){
		if(!intersects(calendarPeriod))
			return this;

		Calendar start = calendarPeriod.getStart().after(this.getStart()) ? this.getStart() : calendarPeriod.getStart();
		Calendar end = calendarPeriod.getEnd().after(this.getEnd()) ? calendarPeriod.getEnd() : this.getEnd();
		return new CalendarPeriod(start, end);
	}

	public boolean intersects(CalendarPeriod calendarPeriod){
		return (calendarPeriod.getStart().before(this.getStart()) && calendarPeriod.getEnd().before(this.getEnd())) ||
				(calendarPeriod.getEnd().after(this.getEnd()) && calendarPeriod.getStart().after(this.getStart())) ||
				calendarPeriod.inside(this) || this.inside(calendarPeriod);
	}

	public boolean inside(CalendarPeriod calendarPeriod){
		return calendarPeriod.getStart().before(this.getStart()) && calendarPeriod.getEnd().after(this.getEnd());
	}

	public boolean before(CalendarPeriod calendarPeriod) {
		return this.getEnd().before(calendarPeriod.getStart());
	}

	public boolean after(CalendarPeriod calendarPeriod){
		return this.getStart().after(calendarPeriod.getEnd());
	}

	public Pair<CalendarPeriod, CalendarPeriod> split(CalendarPeriod calendarPeriod){
		if(calendarPeriod.inside(this)){
			Calendar start0 = this.getStart();
			Calendar end0 = calendarPeriod.getStart();
			CalendarPeriod first = new CalendarPeriod(start0, end0);

			Calendar start1 = calendarPeriod.getEnd();
			Calendar end1 = calendarPeriod.getEnd();
			CalendarPeriod second = new CalendarPeriod(start1, end1);

			return new Pair<>(first, second);
		} else
			return new Pair<>(this, this);
	}



}


