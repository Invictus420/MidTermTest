
package testex;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.response.ExtractableResponse;
import testex.interfaces.IDateFormatter;
import testex.interfaces.IFetcherFactory;
import testex.interfaces.IJokeFetcher;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher
{

    public JokeFetcher(IFetcherFactory fetcherFactory, IDateFormatter dateFormatter)
    {
        this.fetcherFactory = fetcherFactory;
        this.dateFormatter = dateFormatter;
    }

    private final List<String> availableTypes = Arrays.asList("eduprog", "chucknorris", "moma", "tambal");

    private IDateFormatter dateFormatter;
    private IFetcherFactory fetcherFactory;

    public List<String> getAvailableTypes()
    {
        return availableTypes;
    }

    //var ikke public
    public boolean isStringValid(String jokeTokens)
    {
        String[] tokens = jokeTokens.split(",");
        for (String token : tokens)
        {
            if (!availableTypes.contains(token))
            {
                return false;
            }
        }
        return true;
    }

    public Jokes getJokes(String jokesToFetch) throws JokeException
    {
        isStringValid(jokesToFetch);
        Jokes jokes = new Jokes();
        for (IJokeFetcher fetcher : fetcherFactory.getJokeFetchers(jokesToFetch))
        {
            jokes.addJoke(fetcher.getJoke());
        }
        String tzString = dateFormatter.getFormattedDate();
        jokes.setTimeZoneString(tzString);
        return jokes;
    }

}
