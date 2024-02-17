//package test;
//
//
//import model.Movie;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MovieTest {
//
//    private Movie batman;
//
//    @BeforeEach
//    public void setUp() {
//        batman = new Movie("The Batman", 2022);
//    }
//
//    // test the constructor
//    @Test
//    public void testMovie() {
//        assertEquals("The Batman", batman.getMovieName());
//        assertEquals(2022, batman.getMovieYear());
//        assertFalse(batman.getIsWatched());
//    }
//
//    @Test
//    public void testSetMovieDescription() {
//        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
//        assertEquals("In his sophomore year, Batman faces the Riddler", batman.getMovieDescription());
//    }
//
//    @Test
//    public void testSetUserRating() {
//        batman.setUserRating(97);
//        assertEquals(97, batman.getUserRating());
//        batman.setUserRating(95);
//        assertEquals(95, batman.getUserRating());
//    }
//
//    @Test
//    public void testSetMovieWatched() {
//        batman.setMovieWatched();
//        assertEquals(true, batman.getIsWatched());
//    }
//
//    @Test
//    public void testSetDirector() {
//        batman.setDirector("Matt Reeves");
//        assertEquals("Matt Reeves", batman.getDirector());
//    }
//
//    @Test
//    public void testMovieDetailsIfNotWatched() {
//        batman.setDirector("Matt Reeves");
//        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
//        assertEquals("The Batman   (2022)" + System.lineSeparator()
//                + "Directed by: Matt Reeves" + System.lineSeparator()
//                + "Movie Description: " + System.lineSeparator()
//                + "In his sophomore year, Batman faces the Riddler", batman.movieDetails());
//    }
//
//    @Test
//    public void testMovieDetailsIfWatched() {
//        batman.setMovieWatched();
//        batman.setDirector("Matt Reeves");
//        batman.setUserRating(95);
//        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
//        assertEquals("The Batman   (2022)" + System.lineSeparator()
//                + "Directed by: Matt Reeves" + System.lineSeparator()
//                + "Your Rating: 95/100" + System.lineSeparator()
//                + "Movie Description: " + System.lineSeparator()
//                + "In his sophomore year, Batman faces the Riddler", batman.movieDetails());
//    }
//
//
//}
