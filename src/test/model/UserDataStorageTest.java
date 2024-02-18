package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataStorageTest {
    private UserDataStorage allUsers;
    private Movie batman;
    private Movie barbie;
    private MyMovieList jugaad;
    private MyMovieList poop;


    @BeforeEach
    public void setUp() {
        allUsers = new UserDataStorage();
        batman = new Movie("The Batman", 2022);
        batman.setMovieDescription("Batman vs Riddler");
        batman.setDirector("Matt Reeves");
        batman.setUserRating(97);
        barbie = new Movie("Barbie", 2023);
        barbie.setMovieDescription("Barbie World");
        barbie.setDirector("Greta Gerwig");
        barbie.setUserRating(80);
        jugaad = new MyMovieList("jugaad");
        poop = new MyMovieList("poop");

    }

    @Test
    public void testUserDataStorage() {
        assertEquals(0, allUsers.getTotalUsers());
    }

    @Test
    public void testIsUserInDatabase() {
        assertFalse(allUsers.isUserInDatabase("jugaad"));
        allUsers.addUserToDatabase(jugaad);
        allUsers.addUserToDatabase(poop);
        assertTrue(allUsers.isUserInDatabase("jugaad"));
        assertTrue(allUsers.isUserInDatabase("poop"));
        assertFalse(allUsers.isUserInDatabase("buttMan"));
    }

    @Test
    public void testIsUserInDataBaseReturnUser() {
        assertNull(allUsers.isUserInDatabaseReturnUser("jugaad"));
        allUsers.addUserToDatabase(jugaad);
        allUsers.addUserToDatabase(poop);
        assertEquals(jugaad, allUsers.isUserInDatabaseReturnUser("jugaad"));
        assertEquals(poop, allUsers.isUserInDatabaseReturnUser("poop"));
    }

    @Test
    public void testAddUserToDatabase() {
        allUsers.addUserToDatabase(jugaad);
        assertEquals(1, allUsers.getTotalUsers());
        allUsers.addUserToDatabase(poop);
        assertEquals(2, allUsers.getTotalUsers());
        assertEquals(jugaad, allUsers.getUserOrder(1));
        assertEquals(poop, allUsers.getUserOrder(2));
    }

    @Test
    public void testGetTotalUsers() {
        allUsers.addUserToDatabase(jugaad);
        assertEquals(1, allUsers.getTotalUsers());
        allUsers.addUserToDatabase(poop);
        assertEquals(2, allUsers.getTotalUsers());
    }

    @Test
    public void testGetUserOrder() {
        allUsers.addUserToDatabase(jugaad);
        assertEquals(jugaad, allUsers.getUserOrder(1));
        allUsers.addUserToDatabase(poop);
        assertEquals(jugaad, allUsers.getUserOrder(1));
        assertEquals(poop, allUsers.getUserOrder(2));
    }

}
