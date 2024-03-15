//package persistence;
//import model.*;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class JsonReaderUserDataStorageTest extends JsonTestUserDataStorage {
//    @Test
//    void testReaderNonExistentFile() {
//        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/noSuchFile.json");
//        try {
//            UserDataStorage uds = reader.read();
//            fail("IOException expected");
//        } catch (IOException e) {
//            // pass
//        }
//    }
//
//    @Test
//    void testReaderEmptyDatabase() {
//        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testReaderEmptyUsers.json");
//        try {
//            UserDataStorage uds = reader.read();
//            assertEquals(0, uds.getTotalUsers());
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
//
//    @Test
//    void testReaderGeneralDatabase() {
//        JsonReaderUserDataStorage reader = new JsonReaderUserDataStorage("./data/testReaderGeneralUsers.json");
//        try {
//            UserDataStorage uds = reader.read();
//            List<User> users = uds.getUsers();
//            assertEquals(3, users.size());
//            checkUser("jugaadlally",users.get(0), "THE BATMAN", 1 );
//            checkUser("saharah", users.get(1),"BARBIE", 1);
//            checkUser("poopman", users.get(2), "THE BATMAN", 3);
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
//}
