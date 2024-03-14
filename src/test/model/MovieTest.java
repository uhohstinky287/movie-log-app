//package model;
//
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MovieTest {
//
//    private Movie batman;
//    private ArrayList<Integer> totalRatings;
//    private JSONArray totalRatingsJson;
//    private Review r1;
//    private Review r2;
//    private Review r3;
//    private Review r4;
//
//    @BeforeEach
//    public void setUp() {
//        batman = new Movie("The Batman", 2022);
//        totalRatings = new ArrayList<>();
//        totalRatingsJson = new JSONArray();
//        r1 = new Review("jugaad", 1);
//        r2 = new Review("buttman123", 2);
//        r3 = new Review("poop", 3);
//        r4 = new Review("mom", 4);
//
//
//    }
//
//
//    // tests the constructor
//    @Test
//    public void testMovie() {
//        assertEquals("The Batman", batman.getMovieName());
//        assertEquals(2022, batman.getMovieYear());
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
//    public void testSetDirector() {
//        batman.setDirector("Matt Reeves");
//        assertEquals("Matt Reeves", batman.getDirector());
//    }
//
//
//    @Test
//    public void testMovieDetailsUnWatched() {
//        batman.setDirector("Matt Reeves");
//        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
//        assertEquals("The Batman   (2022)" + System.lineSeparator()
//                + "Directed by: Matt Reeves" + System.lineSeparator()
//                + "No ratings yet" + System.lineSeparator()
//                + "Movie Description: " + System.lineSeparator()
//                + "In his sophomore year, Batman faces the Riddler", batman.movieDetailsUnWatched());
//        batman.addToTotalRatings(98);
//        assertEquals("The Batman   (2022)" + System.lineSeparator()
//                + "Directed by: Matt Reeves" + System.lineSeparator()
//                + "Average rating of all users: 98.0/100" + System.lineSeparator()
//                + "Movie Description: " + System.lineSeparator()
//                + "In his sophomore year, Batman faces the Riddler", batman.movieDetailsUnWatched());
//    }
//
//    @Test
//    public void testRatingDetails() {
//        assertEquals("No ratings yet", batman.ratingDetails());
//        batman.addToTotalRatings(100);
//        assertEquals("Average rating of all users: 100.0/100", batman.ratingDetails());
//        batman.addToTotalRatings(99);
//        assertEquals("Average rating of all users: 99.5/100", batman.ratingDetails());
//    }
//
//    @Test
//    public void testGetTotalRatings() {
//        assertEquals(0, batman.getTotalRatings());
//        batman.addToTotalRatings(100);
//        batman.addToTotalRatings(99);
//        assertEquals(2, batman.getTotalRatings());
//    }
//
//    @Test
//    public void testCalculateAverageRating() {
//            assertEquals(0, batman.calculateAverageRating());
//            batman.addToTotalRatings(100);
//            assertEquals(1, batman.getTotalRatings());
//            assertEquals(100, batman.calculateAverageRating());
//            batman.addToTotalRatings(99);
//            assertEquals(99.5, batman.calculateAverageRating());
//            batman.addToTotalRatings(100);
//            assertEquals(99.7, batman.calculateAverageRating());
//    }
//
//    @Test
//    public void testGetAverageRating() {
//        assertEquals(0, batman.getAverageRating());
//        batman.addToTotalRatings(100);
//        assertEquals(1, batman.getTotalRatings());
//        assertEquals(100, batman.getAverageRating());
//        batman.addToTotalRatings(99);
//        assertEquals(99.5, batman.getAverageRating());
//        batman.addToTotalRatings(100);
//        assertEquals(99.7, batman.getAverageRating());
//    }
//
//    @Test
//    public void testAddToTotalRatings() {
//        assertEquals(0, batman.getTotalRatings());
//        batman.addToTotalRatings(100);
//        assertEquals(1, batman.getTotalRatings());
//    }
//
//    @Test
//    public void testToJson() {
//        batman.setDirector("Matt Reeves");
//        batman.setMovieDescription("Emo Batman");
//        batman.setUserRating(100);
//        JSONObject batmanJson = new JSONObject();
//        batmanJson.put("name", "The Batman");
//        batmanJson.put("year", 2022);
//        batmanJson.put("rating", 100);
//        batmanJson.put("description", "Emo Batman");
//        batmanJson.put("director", "Matt Reeves");
//        JSONArray allRatingsJson = new JSONArray();
//        batmanJson.put("allRatings", allRatingsJson);
//
//        assertEquals(batmanJson.toString(), batman.toJson().toString() );
//    }
//
//    @Test
//    public void testTotalRatingsToJson() {
//        assertEquals(totalRatingsJson.toList(), batman.totalRatingsToJson().toList());
//        batman.addToTotalRatings(100);
//        totalRatingsJson.put(100);
//        assertEquals(totalRatingsJson.toList(), batman.totalRatingsToJson().toList());
//    }
//
//    @Test
//    public void testAddReview(){
//        batman.addReview(r1.getUsername(), r1);
//        assertTrue(batman.getAllReviews().contains(r1));
//    }
//
//    @Test
//    public void testGetAllReviews() {
//        batman.addReview(r1.getUsername(), r1);
//        batman.addReview(r2.getUsername(), r2);
//        batman.addReview(r3.getUsername(), r3);
//        batman.addReview(r4.getUsername(), r4);
//        assertTrue(batman.getAllReviews().contains(r1));
//        assertTrue(batman.getAllReviews().contains(r2));
//        assertEquals(r1, batman.getAllReviews().get(0));
//        assertEquals(r2, batman.getAllReviews().get(1));
//    }
//
//
//
//
//
//}
