package testex;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import testex.interfaces.IDateFormatter;

public class DateFormatter implements IDateFormatter
{
    private String timeZone;
    private Date time;

    public DateFormatter(String timeZone, Date time) throws JokeException
    {
        if (!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone) || time == null)
        {
            throw new JokeException("Illegal Time Zone or Date");
        }
        this.time = time;
        this.timeZone = timeZone;

    }

    public String getFormattedDate()
    {
        String dateTimeFormat = "dd MMM yyyy hh:mm aa";
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateTimeFormat);
        simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleFormat.format(time);
    }




}
