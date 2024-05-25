package fr.alib.gotrips.utils;

import java.util.Date;

public class SanitationUtils {
	public static String stringGetNullIfEmpty(String str) {
		return str == null || str.trim().isEmpty() ? null : str;
	}
	
	public static Float floatGetNullIfEmpty(String str) {
		Float val;
		try {
			val = Float.valueOf(str);
		} catch (Exception e) {
			val = null;
		}
		return str == null || str.trim().isEmpty() ? null : val;
	}
	
	public static Long longGetNullIfEmpty(String str) {
		Long val;
		try {
			val = Long.valueOf(str);
		} catch (Exception e) {
			val = null;
		}
		return str == null || str.trim().isEmpty() ? null : val;
	}
	
	public static Date dateGetNullIfEmptyFromTimestamp(String str) {
		Long val = SanitationUtils.longGetNullIfEmpty(str);
		return (str == null || str.trim().isEmpty() || val == null) ? null : new Date(val);
	}
}
