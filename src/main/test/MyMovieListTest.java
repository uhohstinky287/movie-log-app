package test;


import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyMovieListTest {
    private MyMovieList myMovies;
    private AllMoviesList allMovies;
    private Movie batman;
    private Movie barbie;

    @BeforeEach
    public void setUp() {
        myMovies = new MyMovieList("jugs");
        allMovies = new AllMoviesList();
        batman = new Movie("The Batman", 2022);
        barbie = new Movie("Barbie", 2023);
    }

    @Test
    public void testMyMovieList() {
        assertEquals(0, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testGetTotalMoviesSeen() {
        assertEquals(0, myMovies.getTotalMovies());
        myMovies.addMovie(batman);
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











}
