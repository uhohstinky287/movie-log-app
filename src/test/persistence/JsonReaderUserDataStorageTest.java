package persistence;
import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderUserDataStorageTest extends JsonTestUserDataStorage {
    @Test
    void testReaderNonExistentFile() {
        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/noSuchFile.json");
        try {
            UserDataStorage uds = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testReaderEmptyUsers.json");
        try {
            UserDataStorage uds = reader.read();
            assertEquals(0, uds.getTotalUsers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testReaderGeneralUsers.json");
        try {
            UserDataStorage uds = reader.read();
            List<MyMovieList> users = uds.getUsers();
            assertEquals(2, users.size());
            checkUser("jugaadlally", users.get(0));
            checkUser("seeritlally", users.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
