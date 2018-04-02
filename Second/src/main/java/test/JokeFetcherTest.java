package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import testex.*;
import testex.interfaces.IDateFormatter;
import testex.interfaces.IFetcherFactory;
import testex.interfaces.IJokeFetcher;
import testex.jokefetching.ChuckNorris;
import testex.jokefetching.EduJoke;
import testex.jokefetching.Moma;
import testex.jokefetching.Tambal;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest
{
    private static IDateFormatter dateFormatter;
    private static IJokeFetcher eduJoke;
    private static IJokeFetcher moma;
    private static IJokeFetcher chuckNorris;
    private static IJokeFetcher tambal;
    private static IFetcherFactory fetcherFactory;
    private static JokeFetcher jokeFetcher;
    private static List<IJokeFetcher> fetcherList;

    @BeforeClass
    public static void setUp() throws JokeException
    {
        dateFormatter = mock(DateFormatter.class);
        when(dateFormatter.getFormattedDate()).thenReturn("31 mar. 2018 09:16 PM");

        eduJoke = mock(EduJoke.class);
        when(eduJoke.getJoke()).thenReturn(new Joke("eduJoke", "eduJokeURL"));

        moma = mock(Moma.class);
        when(moma.getJoke()).thenReturn(new Joke("momaJoke", "momaJokeURL"));

        chuckNorris = mock(ChuckNorris.class);
        when(chuckNorris.getJoke()).thenReturn(new Joke("chuckNorrisJoke", "chuckNorrisJokeURL"));

        tambal = mock(Tambal.class);
        when(tambal.getJoke()).thenReturn(new Joke("tambalJoke", "tambalJokeURL"));

        fetcherList = new ArrayList<>();
        fetcherList.add(eduJoke);
        fetcherList.add(chuckNorris);
        fetcherList.add(moma);
        fetcherList.add(tambal);

        fetcherFactory = mock(FetcherFactory.class);
        when(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetcherList);

        jokeFetcher = new JokeFetcher(fetcherFactory, dateFormatter);

    }




    @Test
    public void getAvailableTypes1()
    {
        assertThat(jokeFetcher.getAvailableTypes(), hasItems("eduprog", "chucknorris", "moma", "tambal"));
    }

    @Test
    public void getAvailableTypes2()
    {
        assertThat(jokeFetcher.getAvailableTypes().size(), is(equalTo(4)));
    }

    @Test
    public void isStringValidPass_0()
    {
        assertThat(jokeFetcher.isStringValid("eduprog,chucknorris,moma,tambal"), is(equalTo(true)));
    }

    @Test //Order dosent matter as it checks .contains
    public void isStringValidPass_1()
    {
        assertThat(jokeFetcher.isStringValid("chucknorris,eduprog,tambal,moma"), is(equalTo(true)));
    }

    @Test
    public void isStringValidFail_0()
    {
        assertThat(jokeFetcher.isStringValid("eduprog, chucknorris, moma, tambal"), is(equalTo(false)));
    }

    @Test
    public void isStringValidFail_1()
    {
        assertThat(jokeFetcher.isStringValid("eduprog,chucknorris,moma,tamb12al"), is(equalTo(false)));
    }

    @Test
    public void isStringValidFail_3()
    {
        assertThat(jokeFetcher.isStringValid(" , , , "), is(equalTo(false)));
    }


    @Test
    public void isStringValidFail_4()
    {
        assertThat(jokeFetcher.isStringValid("ed12uprog,chucknorr2is,mo2ma,tamb12al"), is(equalTo(false)));
    }


    @Test
    public void getJokes_FetcherMockStateCheck_pass() throws JokeException
    {
//        List<IJokeFetcher> fetchers = fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal"), hasItems(eduJoke, chuckNorris, moma, tambal));
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").size(), is(equalTo(4)));
    }

    @Test
    public void getJokes_eduJokePass_0() throws JokeException
    {
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(0).getJoke().getJoke().equalsIgnoreCase("eduJoke"), is(true));
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(0).getJoke().getReference().equalsIgnoreCase("eduJokeURL"), is(true));
    }


    @Test
    public void getJokes_eduJokePass_1() throws JokeException
    {
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(1).getJoke().getJoke().equalsIgnoreCase("chuckNorrisJoke"), is(true));
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(1).getJoke().getReference().equalsIgnoreCase("chuckNorrisJokeURL"), is(true));
    }


    @Test
    public void getJokes_eduJokePass_2() throws JokeException
    {
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(2).getJoke().getJoke().equalsIgnoreCase("momaJoke"), is(true));
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(2).getJoke().getReference().equalsIgnoreCase("momaJokeURL"), is(true));
    }


    @Test
    public void getJokes_eduJokePass_3() throws JokeException
    {
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(3).getJoke().getJoke().equalsIgnoreCase("tambalJoke"), is(true));
        assertThat(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal").get(3).getJoke().getReference().equalsIgnoreCase("tambalJokeURL"), is(true));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getJokes_eduJokeFail_0() throws JokeException
    {
        fetcherFactory.getJokeFetchers("").get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getJokes_eduJokeFail_1() throws JokeException
    {
        fetcherFactory.getJokeFetchers(",,,").get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getJokes_eduJokeFail_2() throws JokeException
    {
        fetcherFactory.getJokeFetchers("asdfasdf,sadfasdf,asdfsadf,asdfasdf").get(0);
    }

    @Test //With new instances, so the previous tests dosent affect the outcome
    public void getJokes_VerifYNumberOfCallsToGetFormattedDate_pass_0() throws JokeException
    {
        List<IJokeFetcher> fetcherList = new ArrayList<>();
        fetcherList.add(eduJoke);
        fetcherList.add(chuckNorris);
        fetcherList.add(moma);
        fetcherList.add(tambal);
        IDateFormatter testObj1 = mock(DateFormatter.class);
        IFetcherFactory testObj2 = mock(FetcherFactory.class);
        when(dateFormatter.getFormattedDate()).thenReturn("31 mar. 2018 09:16 PM");
        when(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetcherList);

        JokeFetcher testObj3 = new JokeFetcher(testObj2, testObj1);
        testObj3.getJokes("EduJoke,ChuckNorris,Moma,Tambal");
        verify(testObj1, times(1)).getFormattedDate();
        verify(testObj2, times(1)).getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
    }



}