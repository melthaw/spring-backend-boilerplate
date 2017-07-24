package in.clouthink.daas.sbb.shared.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author dz
 */
public class DateTimeUtils {


	/**
	 * Get the start time of specified date
	 *
	 * @param day
	 * @return
	 */
	public static Date startOfDay(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Get the end time of specified date
	 *
	 * @param day
	 * @return
	 */
	public static Date endOfDay(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}


	public static Date newDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date[] dayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);

		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date[] result = new Date[maxDay];
		for (int i = 0; i < maxDay; i++) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, i + 1);
			result[i] = cal.getTime();
		}
		return result;
	}

	public static Integer howOldAreYou(Date birthday) {
		if (birthday == null) {
			return null;
		}

		long timeDiff = System.currentTimeMillis() - birthday.getTime();

		if (timeDiff <= 0) {
			return null;
		}

		int result = (int) (timeDiff / (365 * 24 * 60 * 60 * 1000l));
		if (result <= 0) {
			return null;
		}
		return new Integer(result);
	}

}
