package pt.hospetall.web.util;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftUtil {

	/*
	private static LinkedList<CalendarPeriod> getWeeklyShift(CalendarPeriod calendarPeriod, List<CalendarPeriod> shiftWeeklyPeriod, Calendar current) {
		LinkedList<CalendarPeriod> allShifts = new LinkedList<>();
		final Calendar mutableCurrent = Calendar.getInstance();
		mutableCurrent.setTimeInMillis(current.getTimeInMillis());

		while(mutableCurrent.before(calendarPeriod.calculateEnd())){
			final int currentDayOfTheWeek = current.get(Calendar.DAY_OF_WEEK);
			List<CalendarPeriod> aux = shiftWeeklyPeriod.stream()
					.filter(s -> s.getStart().get(Calendar.DAY_OF_WEEK) == currentDayOfTheWeek)
					.map(s -> {
						Calendar startAux = CalendarUtil.setDay(mutableCurrent, s.getStart());
						Calendar endAux = CalendarUtil.setDay(mutableCurrent, s.calculateEnd());

						while(endAux.before(startAux))
							endAux.add(Calendar.DAY_OF_YEAR, 1);

						return new CalendarPeriod(startAux, endAux);
					})
					.collect(Collectors.toList());

			allShifts.addAll(aux);
			current.add(Calendar.DAY_OF_YEAR, 1);
			current = CalendarUtil.getStartOfDayFromCalendar(current);
		}
		return allShifts;
	}
	*/

}
