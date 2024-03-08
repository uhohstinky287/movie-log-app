package persistence;

import org.junit.jupiter.api.Test;
import model.*;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class JsonWriterUserDataStorageTest extends JsonTestUserDataStorage {


    @Test
    void testWriterInvalidFile() {
        try {
            UserDataStorage uds = new UserDataStorage();
            JsonWriterUserDataStorage writer = new JsonWriterUserDataStorage("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDatabase() {
        try {
            UserDataStorage uds = new UserDataStorage();
            JsonWriterUserDataStorage writer = new JsonWriterUserDataStorage("./data/testWriterEmptyUsers.json");
            writer.open();
            writer.write(uds);
            writer.close();

            JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testWriterEmptyUsers.json");
            uds = reader.read();
            assertEquals(0, uds.getTotalUsers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDatabase() {
        try {
            UserDataStorage uds = new UserDataStorage();
            uds.addUserToDatabase(new MyMovieList("jugaadlally"));
            uds.addUserToDatabase(new MyMovieList("seeritlally"));
            JsonWriterUserDataStorage writer = new JsonWriterUserDataStorage("./data/testWriterGeneralUsers.json");
            writer.open();
            writer.write(uds);
            writer.close();

            JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testWriterGeneralUsers.json");
            uds = reader.read();
            List<MyMovieList> users = uds.getUsers();
            assertEquals(2, users.size());
            checkUser("jugaadlally", users.get(0));
            checkUser("seeritlally", users.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

