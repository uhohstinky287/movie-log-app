package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestUserDataStorage {
    protected void checkUser(String username, User mml, String movieName, Integer reviewsNumber, String friend) {
        assertEquals(username, mml.getUsername());
        assertEquals(movieName, mml.getMovieFromOrder(1).getMovieName());
        assertEquals(reviewsNumber, mml.getMovieFromOrder(1).getTotalRatingsSize());
        assertEquals(friend, mml.getFriends().get(0));
    }
}

