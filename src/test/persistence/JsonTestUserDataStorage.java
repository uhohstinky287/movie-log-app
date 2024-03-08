package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestUserDataStorage {
    protected void checkUser(String username, MyMovieList mml, String movieName) {
        assertEquals(username, mml.getUsername());
        assertEquals(movieName, mml.getMovieFromOrder(1).getMovieName());
    }
}

