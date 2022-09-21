package ir.smartplanning.shared.calender;

import java.util.Date;

public class GregorianCalendar extends Calendar {
	private Date cached = null;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int hour = 0;
	private int minute = 0;
	private int second = 0;

	public GregorianCalendar() {
		super();

		setTime(new Date());
	}

	@Override
	public Object clone() {

		final GregorianCalendar date = new GregorianCalendar();
		date.setTime(getTime());
		return (Object) date;
	}

	 @SuppressWarnings("deprecation")
	 public void clear() {
	 cached = new Date(1970 - 1900, 0, 1, 0, 0, 0);
	 computeFields();
	 }

	@SuppressWarnings("deprecation")
	public void computeFields() {
		year = cached.getYear() + 1900;
		month = cached.getMonth();
		day = cached.getDate();
		hour = cached.getHours();
		minute = cached.getMinutes();
		second = cached.getSeconds();
	}

	@SuppressWarnings("deprecation")
	public void computeTime() {
		cached = new Date(year - 1900, month, day, hour, minute, second);
		computeFields(); // recompute the fields because they may have been
							// rolled
	}

	public Date getTime() {
		computeTime();
		return new Date(cached.getTime());
	}

	public void setTime(Date date) {
		// CommonLogger.log(logger, Level.FINE, "date.getYear() : ",
		// date.getYear());
		cached = new Date(date.getTime());
		computeFields();
	}

	public void setTimeInMillis(long millis) {
		cached = new Date(millis);
		computeFields();
	}

	public long getTimeInMillis() {
		computeTime();
		return cached.getTime();
	}

	@SuppressWarnings("deprecation")
	public int get(int field) {
		computeTime();
		switch (field) {
		case YEAR: {
			return year;
		}
		case MONTH: {
			return month;
		}
		case DAY_OF_MONTH: {
			return day;
		}
		case HOUR: {
			return (hour > 11) ? hour - 12 : hour;
		}
		case AM_PM: {
			if (hour < 12)
				return AM;
			else
				return PM;
		}
		case HOUR_OF_DAY: {
			return hour;
		}
		case MINUTE: {
			return minute;
		}
		case SECOND: {
			return second;
		}
		case DAY_OF_WEEK: {
			computeTime();
			return cached.getDay() + 1;
		}
		case DAY_OF_YEAR: {
			computeTime();
			return compareDate(new Date(cached.getYear(), 0, 0), cached);
		}
		case WEEK_OF_YEAR: {
			computeTime();
			GregorianCalendar weekOneThisYear = new GregorianCalendar();
			weekOneThisYear.setTime(new Date(cached.getYear(), 0, 1));
			if ((7 - weekOneThisYear.get(DAY_OF_WEEK) + 1) < getMinimalDaysInFirstWeek()) {
				// this is actually week two, so roll back for start of week one
				weekOneThisYear.add(DATE, -7);
			}
			weekOneThisYear.set(DAY_OF_WEEK, getFirstDayOfWeek());
			int days = compareDate(weekOneThisYear.getTime(), cached);
			int week = ((days / 7) + 1);
			if (week < 1) {
				return 1;
			} else if (week < 53) {
				return week;
			} else {
				GregorianCalendar weekOneNextYear = new GregorianCalendar();
				weekOneNextYear.setTime(new Date(cached.getYear() + 1, 0, 1));
				if ((7 - weekOneNextYear.get(DAY_OF_WEEK) + 1) < getMinimalDaysInFirstWeek()) {
					// this is actually week two, so roll back for start of week
					// one
					weekOneNextYear.add(DATE, -7);
				}
				weekOneNextYear.set(DAY_OF_WEEK, getFirstDayOfWeek());
				if (cached.before(weekOneNextYear.getTime())) {
					return 53;
				} else {
					return 1;
				}
			}
		}
		}
		return 0;
	}

	public void set(int field, int value) {
		switch (field) {
		case YEAR: {
			year = value;
			break;
		}
		case MONTH: {
			month = value;
			break;
		}
		case DAY_OF_MONTH: {
			day = value;
			break;
		}
		case HOUR: {
			hour = (hour < 12) ? value : (value + 12);
			break;
		}
		case HOUR_OF_DAY: {
			hour = value;
			break;
		}
		case AM_PM: {
			if ((value == AM) && (hour > 11))
				hour -= 12;
			else if ((value == PM) && (hour < 12))
				hour += 12;
			break;
		}
		case MINUTE: {
			minute = value;
			break;
		}
		case SECOND: {
			second = value;
			break;
		}
		case DAY_OF_WEEK: {
			computeTime();
			int dayOfWeek = get(DAY_OF_WEEK);
			day += (value - dayOfWeek);
			computeTime();
			break;
		}
		case DAY_OF_YEAR: {
			computeTime();
			int dayOfYear = get(DAY_OF_YEAR);
			day += (value - dayOfYear);
			computeTime();
			break;
		}
		case WEEK_OF_YEAR: {
			computeTime();
			int dayOfWeek = get(DAY_OF_WEEK);
			set(MONTH, 0);
			set(DATE, 1);
			if ((7 - get(DAY_OF_WEEK) + 1) < getMinimalDaysInFirstWeek()) {
				// this is actually week two, so roll back for start of week one
				add(DATE, -7);
			}
			add(DATE, ((value - 1) * 7));
			set(DAY_OF_WEEK, dayOfWeek);
			computeTime();
			break;
		}
		}
	}

	public void add(int field, int value) {
		switch (field) {
		case YEAR: {
			year += value;
			break;
		}
		case MONTH: {
			month += value;
			break;
		}
		case DAY_OF_MONTH: {
			day += value;
			break;
		}
		case HOUR:
		case HOUR_OF_DAY: {
			hour += value;
			break;
		}
		case MINUTE: {
			minute += value;
			break;
		}
		case SECOND: {
			second += value;
			break;
		}
		case DAY_OF_WEEK: {
			day += value;
			break;
		}
		case DAY_OF_YEAR: {
			day += value;
			break;
		}
		case WEEK_OF_YEAR: {
			day += (value * 7);
			break;
		}
		}
	}

	public static int daysInMonth(Date d) {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.setTime(d);
		gcal.set(MONTH, gcal.get(MONTH) + 1);
		gcal.set(DATE, 1);
		gcal.add(DATE, -1);
		return gcal.get(DATE);
	}

	/**
	 * Calculate the number of days between two dates
	 * 
	 * @param a
	 *            Date
	 * @param b
	 *            Date
	 * @return the difference in days between b and a (b - a)
	 */
	public static int compareDate(Date a, Date b) {
		final Date ta = new Date(a.getTime());
		final Date tb = new Date(b.getTime());
		final long d1 = setHourToZero(ta).getTime();
		final long d2 = setHourToTen(tb).getTime();
		return (int) ((d2 - d1) / 1000 / 60 / 60 / 24);
	}

	/**
	 * Set hour, minutes, second and milliseconds to zero.
	 * 
	 * @param d
	 *            Date
	 * @return Modified date
	 */
	@SuppressWarnings("deprecation")
	public static Date setHourToZero(Date in) {
		final Date d = new Date(in.getTime());
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		// a trick to set milliseconds to zero
		long t = d.getTime() / 1000;
		t = t * 1000;
		return new Date(t);
	}

	/**
	 * Set hour to 10, minutes, second and milliseconds to zero.
	 * 
	 * @param d
	 *            Date
	 * @return Modified date
	 */
	@SuppressWarnings("deprecation")
	public static Date setHourToTen(Date in) {
		final Date d = new Date(in.getTime());
		d.setHours(10);
		d.setMinutes(0);
		d.setSeconds(0);
		// a trick to set milliseconds to zero
		long t = d.getTime() / 1000;
		t = t * 1000;
		return new Date(t);
	}

}
