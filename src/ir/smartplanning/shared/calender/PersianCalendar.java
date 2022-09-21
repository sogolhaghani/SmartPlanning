package ir.smartplanning.shared.calender;

import java.util.Date;
public class PersianCalendar extends GregorianCalendar {


	private static final int ZONE_OFFSET = 15;

	private int persianYear;
	private int persianMonth;
	private int persianDay;
	// use to seperate PersianDate's field and also Parse the DateString based
	// on this delimiter
	private String delimiter = "/";

	private long convertToMilis(long julianDate) {
		return PersianCalendarConstants.MILLIS_JULIAN_EPOCH
				+ julianDate
				* PersianCalendarConstants.MILLIS_OF_A_DAY
				+ PersianCalendarUtils.ceil(getTimeInMillis()
						- PersianCalendarConstants.MILLIS_JULIAN_EPOCH,
						PersianCalendarConstants.MILLIS_OF_A_DAY);
	}

	
	public PersianCalendar() {
		System.out.println("PersianCalendar");
		setTimeZone();
	}

	protected void calculatePersianDate() {
		long julianDate = ((long) Math
				.floor((getTimeInMillis() - PersianCalendarConstants.MILLIS_JULIAN_EPOCH)) / PersianCalendarConstants.MILLIS_OF_A_DAY);
		long PersianRowDate = PersianCalendarUtils.julianToPersian(julianDate);
		long year = PersianRowDate >> 16;
		int month = (int) (PersianRowDate & 0xff00) >> 8;
		int day = (int) (PersianRowDate & 0xff);
		this.persianYear = (int) (year > 0 ? year : year - 1);
		this.persianMonth = month;
		this.persianDay = day;
		System.out.println("calculatePersianDate");
	}

	public boolean isPersianLeapYear() {
		// calculatePersianDate();
		return PersianCalendarUtils.isPersianLeapYear(this.persianYear);
	}

	
	public void setPersianDate(int persianYear, int persianMonth, int persianDay) {
		this.persianYear = persianYear;
		this.persianMonth = persianMonth;
		this.persianDay = persianDay;
		setTimeInMillis(convertToMilis(PersianCalendarUtils.persianToJulian(
				this.persianYear > 0 ? this.persianYear : this.persianYear + 1,
				this.persianMonth - 1, this.persianDay)));
	}

	public int getPersianYear() {
		// calculatePersianDate();
		return this.persianYear;
	}

	/**
	 * 
	 * @return int persian month number
	 */
	public int getPersianMonth() {
		// calculatePersianDate();
		return this.persianMonth + 1;
	}

	/**
	 * 
	 * @return String persian month name
	 */
	public String getPersianMonthName() {
		// calculatePersianDate();
		return PersianCalendarConstants.persianMonthNames[this.persianMonth];
	}

	/**
	 * 
	 * @return int Persian day in month
	 */
	public int getPersianDay() {
		// calculatePersianDate();
		return this.persianDay;
	}

	/**
	 * 
	 * @return String Name of the day in week
	 */
	public String getPersianWeekDayName() {
		switch (get(DAY_OF_WEEK)) {
		case SATURDAY:
			return PersianCalendarConstants.persianWeekDays[0];
		case SUNDAY:
			return PersianCalendarConstants.persianWeekDays[1];
		case MONDAY:
			return PersianCalendarConstants.persianWeekDays[2];
		case TUESDAY:
			return PersianCalendarConstants.persianWeekDays[3];
		case WEDNESDAY:
			return PersianCalendarConstants.persianWeekDays[4];
		case THURSDAY:
			return PersianCalendarConstants.persianWeekDays[5];
		default:
			return PersianCalendarConstants.persianWeekDays[6];
		}

	}

	public int getPersianWeekDay() {
		switch (get(DAY_OF_WEEK)) {
		case SATURDAY:
			return 0;
		case SUNDAY:
			return 1;
		case MONDAY:
			return 2;
		case TUESDAY:
			return 3;
		case WEDNESDAY:
			return 4;
		case THURSDAY:
			return 5;
		default:
			return 6;
		}

	}

	/**
	 * 
	 * @return String of Persian Date ex: شنبه 01 خرداد 1361
	 */
	public String getPersianLongDate() {
		calculatePersianDate();
		return getPersianWeekDayName() + "  "
				+ formatToMilitary(this.persianDay) + "  "
				+ getPersianMonthName() + "  " + this.persianYear;

	}

	public String getPersianLongDateNoYear() {
		calculatePersianDate();
		return getPersianWeekDayName() + "  "
				+ formatToMilitary(this.persianDay) + "  "
				+ getPersianMonthName();

	}

	/**
	 * 
	 * @return String of persian date formatted by
	 *         'YYYY[delimiter]mm[delimiter]dd' default delimiter is '/'
	 */
	public String getPersianShortDate() {
		calculatePersianDate();
		return "" + formatToMilitary(this.persianYear) + delimiter
				+ formatToMilitary(getPersianMonth()) + delimiter
				+ formatToMilitary(this.persianDay);
	}

	private String formatToMilitary(int i) {
		return (i < 9) ? "0" + i : String.valueOf(i);
	}

	/**
	 * add specific amout of fields to the current date for now doesnt handle
	 * before 1 farvardin hejri (before epoch)
	 * 
	 * @param field
	 * @param amount
	 *            <pre>
	 *  Usage:
	 *  {@code
	 *  addPersianDate(Calendar.YEAR, 2);
	 *  addPersianDate(Calendar.MONTH, 3);
	 *  }
	 * </pre>
	 * 
	 *            u can also use Calendar.HOUR_OF_DAY,Calendar.MINUTE,
	 *            Calendar.SECOND, Calendar.MILLISECOND etc
	 */
	//
	public void addPersianDate(int field, int amount) {
		if (amount == 0) {
			return; // Do nothing!
		}

		if (field < 0 || field >= ZONE_OFFSET) {
			throw new IllegalArgumentException();
		}

		if (field == YEAR) {
			setPersianDate(this.persianYear + amount, getPersianMonth(),
					this.persianDay);
			return;
		} else if (field == MONTH) {
			setPersianDate(this.persianYear
					+ ((getPersianMonth() + amount) / 12),
					(getPersianMonth() + amount) % 12, this.persianDay);
			return;
		}
		add(field, amount);
		calculatePersianDate();
	}

	/**
	 * <pre>
	 *    use <code>{@link PersianDateParser}</code> to parse string 
	 *    and get the Persian Date.
	 * </pre>
	 * 
	 * @see PersianDateParser
	 * @param dateString
	 */
	@SuppressWarnings("deprecation")
	public void parse(String dateString) {
		Date date = new Date(2013, 8, 24, 12, 0);

		this.setNewTime(date);// changing the hour of the day to 12
		this.setTimeZone();
		PersianCalendar p = new PersianDateParser(dateString, delimiter)
				.getPersianDate();
		setPersianDate(p.getPersianYear(), p.getPersianMonth(),
				p.getPersianDay());
	}

	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * assign delimiter to use as a separator of date fields.
	 * 
	 * @param delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public String toString() {
		String str = super.toString();
		return str.substring(0, str.length() - 1) + ",PersianDate="
				+ getPersianShortDate() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public void set(int field, int value) {
		super.set(field, value);
		calculatePersianDate();
	}

	@Override
	public void setTimeInMillis(long millis) {
		super.setTimeInMillis(millis);
		calculatePersianDate();
	}

	// @Override
	public void setTimeZone() { 
		calculatePersianDate();
	}

	public String getPersianShortDate(Date date) {

		this.setNewTime(date);
		return this.getPersianShortDate();
	}

	@SuppressWarnings("deprecation")
	public String getPersianLongDate(Date date) {
		System.out.println(date);
		date.setHours(12);
		this.setNewTime(date);
		return this.getPersianLongDate();
	}

	
	@SuppressWarnings("deprecation")
	public void setNewTime(Date date) {
		
		Date date2 = new Date(date.getYear(), date.getMonth(), date.getDate(),
				12, 0);
		super.setTime(date2);
	}

}
