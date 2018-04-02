package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses
        (
                {
                        DateFormatterTest.class,
                        JokeFetcherTest.class
                }
        )

public class TestSuite
{
}
