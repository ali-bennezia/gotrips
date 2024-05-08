package fr.alib.gotrips.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {
	public static long totalDaysWithinDates(Date a, Date b)
	{
		LocalDate lMinDate = a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate lMaxDate = b.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return ChronoUnit.DAYS.between(lMinDate, lMaxDate) + 1;
	}
}
