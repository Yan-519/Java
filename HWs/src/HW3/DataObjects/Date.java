package HW3.DataObjects;

import java.util.Objects;

import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.DataObjects.Helpers.StringConverter;

public class Date extends StringConverter<Date> implements Comparable<Date> {
	private int day, month, year;

	public Date(int day, int month, int year) {
		if(1 <= day && day <= 31)
		this.day = day;
		if(1 <= month && month <= 12)
		this.month = month;
		this.year = year;
	}
	
	public Date() {
		this(0, 0, 0);
	}

	
	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public void setDay(int day) {
		if(1 <= day && day <= 31)
		this.day = day;
	}

	public void setMonth(int month) {
		if(1 <= month && month <= 12)
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	// if "this" after the given "otherDate"
	public boolean isAfter(Date otherDate) {
	    return year > otherDate.year ||
	          (year == otherDate.year && month > otherDate.month) ||
	          (year == otherDate.year && month == otherDate.month && day > otherDate.day);
	}
	
	@Override
	public String toString() {
		return "Date [day=" + day + ", month=" + month + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		return day == other.day && month == other.month && year == other.year;
	}

	@Override
	public int compareTo(Date o) {
	    if (this.equals(o))
	        return 0;
	    
	    if (this.isAfter(o))
	        return 1;
	    
	    return -1;
	}

	@Override
	public String convert() {
		return joiner(day, month, year);
	}

	@Override
	public ConvertorHolder<Date> convert(String in) {
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 3) {
			throw new IllegalArgumentException("Invalid input format for Date: " + in);
		}

		int parsedDay = Integer.parseInt(parts[0]);
		int parsedMonth = Integer.parseInt(parts[1]);
		int parsedYear = Integer.parseInt(parts[2]);

		return new ConvertorHolder<>( new Date(parsedDay, parsedMonth, parsedYear));
	}
	
	
}
