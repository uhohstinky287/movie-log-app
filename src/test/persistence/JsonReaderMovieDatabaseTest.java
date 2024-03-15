package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderMovieDatabaseTest extends JsonTestMovieDatabase {

    @Test
    void testReaderNonExistentFile() {
        JsonReaderMovieDatabase reader = new JsonReaderMovieDatabase("./data/noSuchFile.json");
        try {
            MovieDatabase md = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReaderMovieDatabase reader = new JsonReaderMovieDatabase("./data/testReaderEmptyDatabase.json");
        try {
            MovieDatabase md = reader.read();
            assertEquals(0, md.getTotalMovies());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReaderMovieDatabase reader = new JsonReaderMovieDatabase("./data/testReaderGeneralDatabase.json");
        try {
            MovieDatabase md = reader.read();
            List<Movie> movies = md.getMovies();
            assertEquals(2, movies.size());
            checkMovie("THE BATMAN", 2022, movies.get(0), 3);
            checkMovie("BARBIE", 2023, movies.get(1), 1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}