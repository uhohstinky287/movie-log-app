package persistence;

import org.junit.jupiter.api.Test;
import model.*;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class JsonWriterMovieDatabaseTest extends JsonTestMovieDatabase{


        @Test
        void testWriterInvalidFile() {
            try {
                MovieDatabase md = new MovieDatabase();
                JsonWriterMovieDatabase writer = new JsonWriterMovieDatabase("./data/my\0illegal:fileName.json");
                writer.open();
                fail("IOException was expected");
            } catch (IOException e) {
                // pass
            }
        }

        @Test
        void testWriterEmptyDatabase() {
            try {
                MovieDatabase md = new MovieDatabase();
                JsonWriterMovieDatabase writer = new JsonWriterMovieDatabase("./data/testWriterEmptyDatabase.json");
                writer.open();
                writer.write(md);
                writer.close();

                JsonReaderMovieDatabase reader = new JsonReaderMovieDatabase("./data/testWriterEmptyDatabase.json");
                md = reader.read();
                assertEquals(0, md.getTotalMovies());
            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        }

        @Test
        void testWriterGeneralDatabase() {
            try {
                MovieDatabase md = new MovieDatabase();
                md.addMovieToDatabase(new Movie("THE BATMAN", 2022));
                md.addMovieToDatabase(new Movie("BARBIE", 2023));
                JsonWriterMovieDatabase writer = new JsonWriterMovieDatabase("./data/testWriterGeneralDatabase.json");
                writer.open();
                writer.write(md);
                writer.close();

                JsonReaderMovieDatabase reader = new JsonReaderMovieDatabase("./data/testWriterGeneralDatabase.json");
                md = reader.read();
                List<Movie> movies = md.getMovies();
                assertEquals(2, movies.size());
                checkMovie("THE BATMAN", 2022, movies.get(0));
                checkMovie("BARBIE", 2023, movies.get(1));

            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        }
    }

