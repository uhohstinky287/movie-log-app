//package test;
//
//import model.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class AllMoviesListTest {
//
//    private AllMoviesList allMovies;
//    private Movie batman;
//    private Movie barbie;
//
//
//    @BeforeEach
//    public void setUp() {
//        allMovies = new AllMoviesList();
//        batman = new Movie("The Batman", 2022);
//        barbie = new Movie("Barbie", 2023);
//    }
//
//    @Test
//    public void testAllMoviesList() {
//        assertEquals(0, allMovies.getTotalMovies());
//    }
//
//    @Test
//    public void testAddMovie() {
//        allMovies.addMovie(batman);
//        assertEquals(1, allMovies.getTotalMovies());
//        assertEquals(batman, allMovies.getMovieFromOrderAll(1));
//        allMovies.addMovie(barbie);
//        assertEquals(2, allMovies.getTotalMovies());
//        assertEquals(batman, allMovies.getMovieFromOrderAll(1));
//        assertEquals(barbie, allMovies.getMovieFromOrderAll(2));
//    }
//
//    @Test
//    public void testGetMovieFromOrderAll() {
//        allMovies.addMovie(barbie);
//        allMovies.addMovie(batman);
//        assertEquals(barbie, allMovies.getMovieFromOrderAll(1));
//        assertEquals(batman, allMovies.getMovieFromOrderAll(2));
//    }
//
//    @Test
//    public void getTotalMovies() {
//        assertEquals(0, allMovies.getTotalMovies());
//        allMovies.addMovie(batman);
//        assertEquals(1, allMovies.getTotalMovies());
//        allMovies.addMovie(barbie);
//        assertEquals(2, allMovies.getTotalMovies());
//    }
//    @Test
//    public void testIsMovieInAllList() {
//        assertFalse(allMovies.isMovieInAllList(batman));
//        allMovies.addMovie(batman);
//        allMovies.addMovie(barbie);
//        assertTrue(allMovies.isMovieInAllList(batman));
//        assertTrue(allMovies.isMovieInAllList(barbie));
//    }
//}
