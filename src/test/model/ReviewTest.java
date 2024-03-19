package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest {
    private Review r1;


    @BeforeEach
    public void setUp() {
        r1 = new Review("jugaadlally", 90);
    }


    @Test
    public void testGetUserName() {
        assertEquals("jugaadlally", r1.getUsername());
    }

    @Test
    public void testGetSetRating() {
        assertEquals(90, r1.getRating());
        r1.setRating(91);
        assertEquals(91, r1.getRating());
    }

    @Test
    public void testGetSetWrittenReview() {
        assertEquals("", r1.getWrittenReview());
        r1.setWrittenReview("Man I love this movie");
        assertEquals("Man I love this movie", r1.getWrittenReview());
    }

    @Test
    public void testToJSON() {
        r1.setWrittenReview("yo");
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("username", r1.getUsername());
        reviewJson.put("rating", r1.getRating());
        reviewJson.put("writtenReview", r1.getWrittenReview());

        assertEquals(reviewJson.toString(), r1.toJson().toString());
    }

}
