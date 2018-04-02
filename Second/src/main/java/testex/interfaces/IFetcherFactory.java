package testex.interfaces;

import testex.JokeException;

import java.util.List;

public interface IFetcherFactory
{
    List<String> getAvailableTypes();

    List<IJokeFetcher> getJokeFetchers(String jokesToFetch) throws JokeException;

}
