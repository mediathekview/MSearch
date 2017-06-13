package de.mediathekview.mlib.filmlisten;

import de.mediathekview.mlib.daten.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A singelton to get the test data for Filmlist tests.
 */
public class FilmlistTestData
{
    private static FilmlistTestData instance=null;

    public static FilmlistTestData getInstance() throws URISyntaxException
    {
        if(instance==null)
        {
            instance = new FilmlistTestData();
        }
        return instance;
    }

    private ListeFilme testData;

    private FilmlistTestData() throws URISyntaxException
    {
        super();
        createTestdata();
    }

    private void createTestdata() throws URISyntaxException
    {
        testData = new ListeFilme();
        testData.writeMetaData();

        Film testFilm1 = new Film(UUID.randomUUID(),
                new ArrayList<>(),
                Sender.ARD,
                "TestTitel",
                "TestThema",
                LocalDateTime.parse("2017-01-01T23:55:00"),
                Duration.of(10, ChronoUnit.MINUTES),
                new URI("http://www.example.org/"));
        testFilm1.setBeschreibung("Test beschreibung.");
        testFilm1.addUrl(Qualities.SMALL, new FilmUrl(new URI("http://example.org/klein.mp4"), 42l));
        testFilm1.addUrl(Qualities.NORMAL, new FilmUrl(new URI("http://example.org/Test.mp4"), 42l));
        testFilm1.addUrl(Qualities.HD, new FilmUrl(new URI("http://example.org/hd.mp4"), 42l));


        Film testFilm2 = new Film(UUID.randomUUID(),
                new ArrayList<>(),
                Sender.ARD,
                "TestTitel",
                "TestThema",
                LocalDateTime.parse("2017-01-01T23:55:00"),
                Duration.of(10, ChronoUnit.MINUTES),
                new URI("http://www.example.org/2"));
        testFilm2.setBeschreibung("Test beschreibung.");
        testFilm2.addUrl(Qualities.SMALL, new FilmUrl(new URI("http://example.org/klein2.mp4"), 42l));
        testFilm2.addUrl(Qualities.NORMAL, new FilmUrl(new URI("http://example.org/Test2.mp4"), 42l));
        testFilm2.addUrl(Qualities.HD, new FilmUrl(new URI("http://example.org/hd2.mp4"), 42l));

        Film testFilm3 = new Film(UUID.randomUUID(),
                new ArrayList<>(),
                Sender.BR,
                "TestTitel",
                "TestThema2",
                LocalDateTime.parse("2017-01-01T23:55:00"),
                Duration.of(10, ChronoUnit.MINUTES),
                new URI("http://www.example.org/"));
        testFilm3.setBeschreibung("Test beschreibung.");
        testFilm3.addUrl(Qualities.SMALL, new FilmUrl(new URI("http://example.org/klein.mp4"), 42l));
        testFilm3.addUrl(Qualities.NORMAL, new FilmUrl(new URI("http://example.org/Test.mp4"), 42l));
        testFilm3.addUrl(Qualities.HD, new FilmUrl(new URI("http://example.org/hd.mp4"), 42l));

        testData.add(testFilm1);
        testData.add(testFilm2);
        testData.add(testFilm3);
    }

    public ListeFilme getTestData()
    {
        return testData;
    }
}