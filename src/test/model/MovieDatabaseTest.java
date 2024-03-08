package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MovieDatabaseTest {

    private MovieDatabase allMovies;
    private Movie batman;
    private Movie barbie;
    private Movie theBatmanFromDatabase;
    private Movie oppenheimer;
    private Movie barbieOld;


    @BeforeEach
    public void setUp() {
        allMovies = new MovieDatabase();
        batman = new Movie("The Batman", 2022);
        barbie = new Movie("Barbie", 2023);
        oppenheimer = new Movie("Oppenheimer", 2023);
        barbieOld = new Movie("Barbie", 2001);
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
        assertFalse(allMovies.isMovieInDatabase(barbieOld));
        assertFalse(allMovies.isMovieInDatabase(oppenheimer));
    }

    @Test
    public void testIsMovieInDatabaseReturnMovie() {
        assertNull(allMovies.isMovieInDatabaseReturnMovie(batman));
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        assertEquals(theBatmanFromDatabase, allMovies.isMovieInDatabaseReturnMovie(batman));
        allMovies.addMovieToDatabase(barbie);
        assertEquals(barbie, allMovies.isMovieInDatabaseReturnMovie(barbie));
        assertNull(allMovies.isMovieInDatabaseReturnMovie(oppenheimer));
        assertNull(allMovies.isMovieInDatabaseReturnMovie(barbieOld));
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

    @Test
    public void testAddToAverageRating() {
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        assertEquals(0, theBatmanFromDatabase.getTotalRatings());
        allMovies.addToAverageRating(theBatmanFromDatabase, 100);
        assertEquals(1, theBatmanFromDatabase.getTotalRatings());
    }

    @Test
    public void testGetAverageRatingFromDatabase() {
        allMovies.addMovieToDatabase(theBatmanFromDatabase);
        assertEquals(0, allMovies.getAverageRatingFromDatabase("The Batman", 2022));
        allMovies.addToAverageRating(theBatmanFromDatabase,100);
        assertEquals(100.0, allMovies.getAverageRatingFromDatabase("The Batman", 2022));
        assertEquals(0.0, allMovies.getAverageRatingFromDatabase("The Batman", 2021));


    }


}
