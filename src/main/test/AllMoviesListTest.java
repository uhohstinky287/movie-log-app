package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AllMoviesListTest {

    private AllMoviesList allMovies;
    private Movie batman;
    private Movie barbie;
    private Movie theBatmanFromDatabase;


    @BeforeEach
    public void setUp() {
        allMovies = new AllMoviesList();
        batman = new Movie("The Batman", 2022);
        barbie = new Movie("Barbie", 2023);
        theBatmanFromDatabase = new Movie("The Batman", 2022);
        theBatmanFromDatabase.setDirector("Matt Reeves");
        theBatmanFromDatabase.setMovieDescription("Emo Batman");
    }

    @Test
    public void testAllMoviesList() {
        assertEquals(0, allMovies.getTotalMovies());
    }

    @Test
    public void testIsMovieInDatabase() {
        assertFalse(allMovies.isMovieInDatabase(batman));
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        allMovies.addMovieToDatabase(barbie);
        assertTrue(allMovies.isMovieInDatabase(batman));
        assertTrue(allMovies.isMovieInDatabase(barbie));
    }

    @Test
    public void testIsMovieInDatabaseReturnMovie() {
        assertNull(allMovies.isMovieInDatabaseReturnMovie(batman));
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        assertEquals(theBatmanFromDatabase, allMovies.isMovieInDatabaseReturnMovie(batman));
    }

    @Test
    public void testProvideDetailsUnwatched() {
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        assertEquals("The Batman   (2022)" + System.lineSeparator()
                + "Directed by: Matt Reeves" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + "Emo Batman", allMovies.provideDetailsUnwatched(theBatmanFromDatabase));
    }

    @Test
    public void testAddMovieToDatabase() {
        allMovies.addMovieToDatabase(batman);
        assertEquals(1, allMovies.getTotalMovies());
        assertEquals(batman, allMovies.getMovieFromOrderAll(1));
        allMovies.addMovieToDatabase(barbie);
        assertEquals(2, allMovies.getTotalMovies());
        assertEquals(batman, allMovies.getMovieFromOrderAll(1));
        assertEquals(barbie, allMovies.getMovieFromOrderAll(2));
    }

    @Test
    public void testGetMovieFromOrderAll() {
        allMovies.addMovieToDatabase(barbie);
        allMovies.addMovieToDatabase(batman);
        assertEquals(barbie, allMovies.getMovieFromOrderAll(1));
        assertEquals(batman, allMovies.getMovieFromOrderAll(2));
    }

    @Test
    public void getTotalMovies() {
        assertEquals(0, allMovies.getTotalMovies());
        allMovies.addMovieToDatabase(batman);
        assertEquals(1, allMovies.getTotalMovies());
        allMovies.addMovieToDatabase(barbie);
        assertEquals(2, allMovies.getTotalMovies());
    }


}
