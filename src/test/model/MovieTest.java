package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private Movie batman;
    private JSONObject reviewsJson;
    private Review r1;
    private Review r2;
    private Review r3;
    private Review r4;
    private Review r5;

    @BeforeEach
    public void setUp() {
        batman = new Movie("The Batman", 2022);
        r1 = new Review("jugaad", 1);
        r2 = new Review("buttman123", 2);
        r3 = new Review("poop", 3);
        r4 = new Review("mom", 4);
        r5 = new Review("jugaad", 10);
        reviewsJson = new JSONObject();


    }


    // tests the constructor
    @Test
    public void testMovie() {
        assertEquals("The Batman", batman.getMovieName());
        assertEquals(2022, batman.getMovieYear());
    }

    @Test
    public void testGetAverageRating() {
        assertEquals(0, batman.getAverageRating());
        batman.addReview(r1.getUsername(),r1);
        assertEquals(1, batman.getTotalRatingsSize());
        assertEquals(1.0, batman.getAverageRating());
        batman.addReview(r2.getUsername(), r2);
        assertEquals(1.5, batman.getAverageRating());
        batman.addReview(r3.getUsername(), r3);
        assertEquals(2.0, batman.getAverageRating());
    }

    @Test
    public void testSetMovieDescription() {
        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
        assertEquals("In his sophomore year, Batman faces the Riddler", batman.getMovieDescription());
    }

    @Test
    public void testSetDirector() {
        batman.setDirector("Matt Reeves");
        assertEquals("Matt Reeves", batman.getDirector());
    }

    @Test
    public void testSetReviews() {
        //
    }



    @Test
    public void testMovieDetailsUnWatched() {
        batman.setDirector("Matt Reeves");
        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
        assertEquals("The Batman   (2022)" + System.lineSeparator()
                + "Directed by: Matt Reeves" + System.lineSeparator()
                + "No ratings yet" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + "In his sophomore year, Batman faces the Riddler", batman.movieDetailsUnWatched());
        batman.addReview(r1.getUsername(), r1);
        assertEquals("The Batman   (2022)" + System.lineSeparator()
                + "Directed by: Matt Reeves" + System.lineSeparator()
                + "Average rating of all users: 1.0/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + "In his sophomore year, Batman faces the Riddler", batman.movieDetailsUnWatched());
    }

    @Test
    public void testMovieDetailsWatched() {
        batman.setDirector("Matt Reeves");
        batman.setMovieDescription("In his sophomore year, Batman faces the Riddler");
        batman.addReview(r1.getUsername(), r1);
        batman.addReview(r2.getUsername(), r2);
        assertEquals("The Batman   (2022)" + System.lineSeparator()
                + "Directed by: Matt Reeves" + System.lineSeparator()
                + "Your Rating: 1/100"
                + "  ...  Average rating of all users: 1.5/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + "In his sophomore year, Batman faces the Riddler" + System.lineSeparator()
                + batman.getUserWrittenReviewForMovieDetailsWatched(r1.getUsername())
                , batman.movieDetailsWatched(r1.getUsername()));
    }

    @Test
    public void testRatingDetails() {
        assertEquals("No ratings yet", batman.ratingDetails());
        batman.addReview(r1.getUsername(), r1);
        assertEquals("Average rating of all users: 1.0/100", batman.ratingDetails());
        batman.addReview(r2.getUsername(), r2);
        assertEquals("Average rating of all users: 1.5/100", batman.ratingDetails());
    }

    @Test
    public void testGetTotalRatingsSize() {
        assertEquals(0, batman.getTotalRatingsSize());
        batman.addReview(r1.getUsername(), r1);
        batman.addReview(r2.getUsername(), r2);
        assertEquals(2, batman.getTotalRatingsSize());
    }

    @Test
    public void testAddReview() {
        batman.addReview(r1.getUsername(), r1);
        assertEquals(1, batman.getAllReviews().size());
        batman.addReview(r5.getUsername(), r5);
        assertEquals(1, batman.getAllReviews().size());
        assertEquals(r1, batman.getReviewsMap().get(r1.getUsername()));
    }


    @Test
    public void testChangeReview() {
        batman.addReview(r1.getUsername(), r1);
        batman.changeReview(r1.getUsername(), r5);
        batman.getUserReview(r1.getUsername());
    }

    @Test
    public void testGetUserReview() {
        assertNull(batman.getUserReview(r1.getUsername()));
        batman.addReview(r1.getUsername(), r1);
        assertEquals(r1, batman.getUserReview(r1.getUsername()));
    }

    @Test
    public void testGetAllReviews() {
        batman.addReview(r1.getUsername(), r1);
        batman.addReview(r2.getUsername(), r2);
        batman.addReview(r3.getUsername(), r3);
        batman.addReview(r4.getUsername(), r4);
        assertTrue(batman.getAllReviews().contains(r1));
        assertTrue(batman.getAllReviews().contains(r2));
        assertEquals(r1, batman.getAllReviews().get(0));
        assertEquals(r2, batman.getAllReviews().get(1));
    }

    @Test
    public void testGetUserWrittenReviewForMovieDetailsWatched() {
        batman.addReview(r1.getUsername(), r1);
        assertEquals("", batman.getUserWrittenReviewForMovieDetailsWatched(r1.getUsername()));
        r1.setWrittenReview("yoo");
        assertEquals("Your review:\nyoo", batman.getUserWrittenReviewForMovieDetailsWatched(r1.getUsername()) );

    }

    @Test
    public void testToJson() {
        batman.setDirector("Matt Reeves");
        batman.setMovieDescription("Emo Batman");
        JSONObject batmanJson = new JSONObject();
        batmanJson.put("name", "The Batman");
        batmanJson.put("year", 2022);
        batmanJson.put("description", "Emo Batman");
        batmanJson.put("director", "Matt Reeves");
        batmanJson.put("reviews", batman.reviewsToJson());

        assertEquals(batmanJson.toString(), batman.toJson().toString() );
    }


    @Test
    public void testReviewsToJson() {
        assertEquals(reviewsJson.toMap(), batman.reviewsToJson().toMap());
        batman.addReview(r1.getUsername(), r1);
        reviewsJson.put(r1.getUsername(), r1);

        assertEquals(reviewsJson.toMap(), batman.getReviewsMap());

    }









}
