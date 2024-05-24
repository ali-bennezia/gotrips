package fr.alib.gotrips.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	public static long totalDaysWithinDates(Date a, Date b)
	{
		LocalDate lMinDate = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate lMaxDate = b.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.DAYS.between(lMinDate, lMaxDate) + 1;
	}
	
	public static boolean areDatesInSameDay(Date a, Date b)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(a);
		cal2.setTime(b);
		return (
				cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				);
	}
}
