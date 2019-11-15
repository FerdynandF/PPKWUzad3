package pl.ferdynand.calendar.ui.model;

import pl.ferdynand.calendar.exceptions.DateFormatException;

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(String dayMonthYear) throws DateFormatException{
        String[] numbers = dayMonthYear.split("[.]");
        if(numbers.length != 3)
            throw new DateFormatException("Wrong date format, should be day.month.year");
        this.day =  Integer.parseInt(numbers[0]);
        this.month = Integer.parseInt(numbers[1]);
        this.year = Integer.parseInt(numbers[2]);
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public Date setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public Date setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDay() {
        return day;
    }

    public Date setDay(int day) {
        this.day = day;
        return this;
    }

    @Override
    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }
}
