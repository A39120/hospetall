package pt.hospetall.web.util;

import java.util.Calendar;

public class CalendarUtil {

	public static Calendar getStartDay(long timeInMillis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		return getStartOfDayFromCalendar(calendar);
	}

	public static Calendar getStartOfDayFromCalendar(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public static Calendar getEndOfDayFromCalendar(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);

		return calendar;
	}

	public static Calendar incrementDay(Calendar calendar){
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar;
	}

	public static long getTimeInMillisFromDateInMillis(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return getTimeInMillisFromCalendar(calendar);
	}

	public static long getTimeInMillisFromCalendar(Calendar calendar){
		int millisecond = calendar.get(Calendar.MILLISECOND);
		int second = calendar.get(Calendar.SECOND) * 1000;
		int minute = calendar.get(Calendar.MINUTE) * 60 * 1000;
		int hour = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000;

		return hour + minute + second + millisecond;
	}

	public static Calendar setCurrentDay(Calendar calendar){
		return setDay(Calendar.getInstance(), calendar);
	}

	/**
	 * Sets the day from calendar 1 to calendar 2 without changing the time of calendar 2
	 */
	public static Calendar setDay(Calendar from, Calendar to){
		to.set(Calendar.YEAR, from.get(Calendar.YEAR));
		to.set(Calendar.MONTH, from.get(Calendar.MONTH));
		to.set(Calendar.DAY_OF_MONTH, from.get(Calendar.DAY_OF_MONTH));
		return to;
	}

	public static boolean weekDayIsSame(Calendar calendar0, Calendar calendar1){
		int weekDay0 = calendar0.get(Calendar.WEEK_OF_YEAR);
		int weekDay1 = calendar1.get(Calendar.WEEK_OF_YEAR);
		return weekDay0 == weekDay1;
	}

	public static boolean timeIsAfter(Calendar calendar0, Calendar calendar1){
		int startHour0 = calendar0.get(Calendar.HOUR_OF_DAY);
		int startHour1 = calendar1.get(Calendar.HOUR_OF_DAY);
		if(startHour0 > startHour1) return true;
		else if(startHour0 < startHour1) return false;
		int startMinute0 = calendar0.get(Calendar.MINUTE);
		int startMinute1 = calendar1.get(Calendar.MINUTE);
		if(startMinute0 > startMinute1) return true;
		else if(startMinute0 < startMinute1) return false;
		int startSecond0 = calendar0.get(Calendar.SECOND);
		int startSecond1 = calendar1.get(Calendar.SECOND);
		return startSecond0 > startSecond1;
	}

}
