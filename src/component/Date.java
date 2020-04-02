package component;

import utility.DateUtility;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

public class Date implements Comparable<Date>
{
    private int day;
    private int month;
    private int year;

    public Date(LocalDateTime dateTime)
    {
        day = dateTime.getDayOfMonth();
        month = dateTime.getMonthValue();
        year = dateTime.getYear();
    }

    public Date(int day, int month, int year)
    {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Return a Date object initialized to today (local system)
     */
    public static Date now()
    {
        /* Using local system time (through LocalDateTime) to get current date-stats */
        return new Date(LocalDateTime.now().getDayOfMonth(),
                        LocalDateTime.now().getMonthValue(),
                        LocalDateTime.now().getYear());
    }

    public int getDay(){ return day; }

    public void setDay(int day){this.day = day;}

    public int getMonth(){ return month; }

    private void setMonth(int month){ this.month = month; }

    public int getYear(){ return year; }

    private void setYear(int year){ this.year = year; }

    /**
     * Returns age calculated - based on Date instance value - and current day/month/year
     */
    public int getAge()
    {
        LocalDateTime now = LocalDateTime.now();

        int dayNow = now.getDayOfMonth();
        int monthNow = now.getMonthValue();
        int yearNow = now.getYear();

        if((yearNow - year) < 0){return -1;} // we do NOT allow time travellers in the water or club, unless
        // from the past

        int yearAge = yearNow - year;
        if((monthNow - month) < 0) // -1 if it more than a month before birthday
        {
            yearAge -= 1;
        }
        if((monthNow - month) == 0) // if same month, check day of month; -1 if now is earlier than b-day
        {
            if((dayNow - day) < 0)
            {
                yearAge -= 1;
            }
        }

        return yearAge;
    }


    public boolean isBirthDay()
    {
        LocalDateTime now = LocalDateTime.now();

        int dayNow = now.getDayOfMonth();
        int monthNow = now.getMonthValue();

        if((day == dayNow) && (month == monthNow)){return true;}
        /* vode only reaches this point if it is not the birthdate today */
        return false;
    }

    @Override
    public String toString()
    {
        return day + ". " + DateUtility.month(month) + " " + year;
        // smuk men forældet, fordi dato blev præsenteret på engelsk
        //return day + StringDecorator.getOrdinalIndicator(day) + " of " + DateUtility.month(month) + " " + year;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof Date)) return false;
        Date date = (Date)o;
        return getDay() == date.getDay() &&
               getMonth() == date.getMonth() &&
               getYear() == date.getYear();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getDay(), getMonth(), getYear());
    }

    @Override
    public int compareTo(Date date)
    {
        int yearComparison = getYear() - date.getYear();
        int monthComparison = getMonth() - date.getMonth();
        int dayComparison = getDay() - date.getDay();

        int comparison = (365 * yearComparison) + (30 * monthComparison) + dayComparison;
        return comparison;
    }
}
