package model;


import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyMovieListTest {
    private MyMovieList myMovies;
    private Movie batman;
    private Movie barbie;
    private Movie batmanRated;
    private Movie oppenheimer;
    private Movie barbieOld;

    @BeforeEach
    public void setUp() {
        myMovies = new MyMovieList("jugs");
        batman = new Movie("The Batman", 2022);
        barbie = new Movie("Barbie", 2023);
        oppenheimer = new Movie("Oppenheimer", 2023);
        barbieOld = new Movie("Barbie", 2001);
        batmanRated = new Movie("The Batman", 2022);
        batmanRated.setUserRating(97);
        batmanRated.setMovieDescription("Emo Batman");
        batmanRated.setDirector("Matt Reeves");
    }

    //tests the constructor
    @Test
    public void testMyMovieList() {
        assertEquals(0, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testGetTotalMoviesSeen() {
        assertEquals(0, myMovies.getTotalMoviesSeen());
        myMovies.addMovie(batman);
        assertEquals(1, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testGetMovieFromOrder() {
        myMovies.addMovie(batman);
        assertEquals(batman, myMovies.getMovieFromOrder(1));
    }

    @Test
    public void testGetUserName() {
        assertEquals("jugs", myMovies.getUsername());
    }

    @Test
    public void testIsMovieInMyMovieList() {
        assertFalse(myMovies.isMovieInMyMovieList(batman));
        myMovies.addMovie(batman);
        assertFalse(myMovies.isMovieInMyMovieList(barbie));
        assertTrue(myMovies.isMovieInMyMovieList(batman));
        myMovies.addMovie(barbie);
        assertTrue(myMovies.isMovieInMyMovieList(batman));
        assertTrue(myMovies.isMovieInMyMovieList(barbie));
        assertFalse(myMovies.isMovieInMyMovieList(barbieOld));
        assertFalse(myMovies.isMovieInMyMovieList(oppenheimer));
    }

    @Test
    public void testViewMoviesNotEmpty() {
        myMovies.addMovie(batman);
        assertEquals("\nThe Batman  (2022)  0/100", myMovies.viewMoviesNotEmpty());
        myMovies.addMovie(barbie);
        assertEquals("\nThe Batman  (2022)  0/100\nBarbie  (2023)  0/100", myMovies.viewMoviesNotEmpty());
    }

    @Test
    public void testAddMovie() {
        myMovies.addMovie(barbie);
        assertEquals(1, myMovies.getTotalMoviesSeen());
        myMovies.addMovie(batman);
        assertEquals(2, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testProvideDetailsWatched() {
        myMovies.addMovie(batmanRated);
        assertEquals("The Batman   (2022)" + System.lineSeparator()
                + "Directed by: Matt Reeves" + System.lineSeparator()
                + "Your Rating: 97/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + "Emo Batman", myMovies.provideDetailsWatched(batmanRated));
    }

    @Test
    public void testIsMovieInMyListReturnMovie() {
        assertNull(myMovies.isMovieInMyListReturnMovie(batmanRated));
        myMovies.addMovie(batmanRated);
        assertEquals(batmanRated, myMovies.isMovieInMyListReturnMovie(batman));
        assertNull(myMovies.isMovieInMyListReturnMovie(barbie));
        myMovies.addMovie(barbie);
        assertEquals(barbie, myMovies.isMovieInMyListReturnMovie(barbie));
        assertNull(myMovies.isMovieInMyListReturnMovie(barbieOld));
        assertNull(myMovies.isMovieInMyListReturnMovie(oppenheimer));
    }


}
