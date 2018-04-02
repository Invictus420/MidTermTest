package testex;

import testex.interfaces.IFetcherFactory;
import testex.interfaces.IJokeFetcher;
import testex.jokefetching.ChuckNorris;
import testex.jokefetching.EduJoke;
import testex.jokefetching.Moma;
import testex.jokefetching.Tambal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FetcherFactory implements IFetcherFactory
{
    private final List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorrisJoke", "MomaJoke", "Tambal");

    @Override
    public List<String> getAvailableTypes()
    {
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) throws JokeException
    {
        List<IJokeFetcher> fetchers = new ArrayList<>();

        // Suggested fix to method!
        if (jokesToFetch.equalsIgnoreCase(",,,"))
        {
            throw new JokeException("Ulovlig streng!");

        }
        String[] tokens = jokesToFetch.split(",");
        for (String token : tokens)
        {
            if (!getAvailableTypes().contains(token))
            {
                throw new JokeException("Ulovlig streng!");
            }
        }

        for (int i = 0; i < tokens.length; i++)
        {
            if (tokens[i].equalsIgnoreCase("EduJoke"))
            {
                fetchers.add(new EduJoke());
            }
            if (tokens[i].equalsIgnoreCase("ChuckNorrisJoke"))
            {
                fetchers.add(new ChuckNorris());
            }
            if (tokens[i].equalsIgnoreCase("MomaJoke"))
            {
                fetchers.add(new Moma());
            }
            if (tokens[i].equalsIgnoreCase("Tambal"))
            {
                fetchers.add(new Tambal());
            }

        }
        return fetchers;

    }
}