package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestUserDataStorage {
    protected void checkUser(String username, MyMovieList mml) {
        assertEquals(username, mml.getUsername());
    }
}

