package test;

import org.junit.BeforeClass;
import org.junit.Test;
import testex.DateFormatter;
import testex.JokeException;
import testex.interfaces.IDateFormatter;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DateFormatterTest
{
    private IDateFormatter testObject;

    @Test(expected = JokeException.class) // Testing illegal string
    public void getFormattedDateFail_0() throws JokeException
    {
        testObject = new DateFormatter("imSupposedToFail!", new Date());
        testObject.getFormattedDate();
    }

    @Test(expected = JokeException.class) // Testing illegal string
    public void getFormattedDateFail_1() throws JokeException
    {
        testObject = new DateFormatter("", new Date());
        testObject.getFormattedDate();
    }

    @Test(expected = JokeException.class) // Testing illegal string
    public void getFormattedDateFail_2() throws JokeException
    {
        testObject = new DateFormatter(" ", new Date());
        testObject.getFormattedDate();
    }

    @Test(expected = JokeException.class) // Testing illegal date object, would like to change the code, so a joke exception is thrown instead
    public void getFormattedDateFail_3() throws JokeException
    {
        testObject = new DateFormatter("Europe/Copenhagen", null);
        testObject.getFormattedDate();
    }

    @Test
    public void getFormattedDatePass() throws JokeException
    {
        testObject = new DateFormatter("Europe/Copenhagen", new Date(1522669036122l)); //magic number
        assertThat(testObject.getFormattedDate(), is(equalTo("02 Apr 2018 01:37 PM"))); //
    }

}