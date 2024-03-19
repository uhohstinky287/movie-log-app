package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataStorageTest {
    private UserDataStorage allUsers;
    private Movie batman;
    private Movie barbie;
    private User jugaad;
    private User poop;
    private User fatty;



    @BeforeEach
    public void setUp() {
        allUsers = new UserDataStorage();
        jugaad = new User("jugaad");
        poop = new User("poop");
        fatty = new User("poop");

    }

    @Test
    public void testUserDataStorage() {
        assertEquals(0, allUsers.getTotalUsers());
    }

    @Test
    public void testGetTotalUsers() {
        assertEquals(0, allUsers.getTotalUsers());
        allUsers.addUserToDatabase(jugaad);
        assertEquals(1, allUsers.getTotalUsers());
    }

    @Test
    public void testGetUsers() {
        assertTrue(allUsers.getUsers().isEmpty());
        allUsers.addUserToDatabase(jugaad);
        assertEquals(jugaad, allUsers.getUsers().get(0));
    }

    @Test
    public void testSetAllUsersMap() {
        Map<String, User> newMapForTest = new LinkedHashMap<>();
        newMapForTest.put("poop", poop);
        allUsers.addUserToDatabase(jugaad);
        assertEquals(jugaad, allUsers.getUsers().get(0));
        allUsers.setAllUsersMap(newMapForTest);
        assertEquals(poop, allUsers.getUsers().get(0));
    }


    @Test
    public void testIsUserInDatabase() {
        assertFalse(allUsers.isUserInDataBase("jugaad"));
        allUsers.addUserToDatabase(jugaad);
        allUsers.addUserToDatabase(poop);
        assertTrue(allUsers.isUserInDataBase("jugaad"));
        assertTrue(allUsers.isUserInDataBase("poop"));
        assertFalse(allUsers.isUserInDataBase("buttMan"));
    }

    @Test
    public void testReturnUserFromDatabase() {
        assertNull(allUsers.returnUserFromDatabase("jugaad"));
        allUsers.addUserToDatabase(jugaad);
        allUsers.addUserToDatabase(poop);
        assertEquals(jugaad, allUsers.returnUserFromDatabase("jugaad"));
        assertEquals(poop, allUsers.returnUserFromDatabase("poop"));
    }


    @Test
    public void testAddUserToDatabase() {
        allUsers.addUserToDatabase(jugaad);
        assertEquals(1, allUsers.getTotalUsers());
        allUsers.addUserToDatabase(poop);
        assertEquals(2, allUsers.getTotalUsers());
        assertEquals(jugaad, allUsers.getUsers().get(0));
        assertEquals(poop, allUsers.getUsers().get(1));
    }


    @Test
    public void testOverrideUserData() {
        allUsers.overrideUserData(jugaad);
        assertEquals(0, allUsers.getUsers().indexOf(jugaad));
        allUsers.overrideUserData(jugaad);
        assertEquals(0, allUsers.getUsers().indexOf(jugaad));
        allUsers.overrideUserData(poop);
        assertEquals(0, allUsers.getUsers().indexOf(jugaad));
        assertEquals(1, allUsers.getUsers().indexOf(poop));
        assertEquals(poop, allUsers.getUsers().get(1));
        allUsers.overrideUserData(fatty);
        assertEquals(fatty, allUsers.getUsers().get(1));
    }

    @Test
    public void testToJson() {
        JSONObject userDataJson = new JSONObject();
        userDataJson.put("users", allUsers.usersToJson());

        assertEquals(userDataJson.toString(), allUsers.toJson().toString());
    }

    @Test
    public void testUsersToJson() {
        JSONObject usersJson = new JSONObject();
        assertEquals(usersJson.toMap(), allUsers.usersToJson().toMap());
        allUsers.addUserToDatabase(jugaad);
        usersJson.put("jugaad", jugaad.toJson());
        assertEquals(usersJson.toMap(), allUsers.usersToJson().toMap());
    }

}
