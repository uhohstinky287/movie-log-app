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
    private MyMovieList fatty;


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
        fatty = new MyMovieList("poop");

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
        assertEquals(jugaad, allUsers.getUserOrder(0));
        assertEquals(poop, allUsers.getUserOrder(1));
    }

    @Test
    public void testGetUserPosition() {
        allUsers.addUserToDatabase(jugaad);
        assertEquals(0, allUsers.getUserPosition("jugaad"));
        allUsers.addUserToDatabase(poop);
        assertEquals(0, allUsers.getUserPosition("jugaad"));
        assertEquals(1, allUsers.getUserPosition("poop"));
    }

    @Test
    public void testOverrideData() {
        allUsers.overrideUserData(jugaad);
        assertEquals(0, allUsers.getUserPosition("jugaad"));
        allUsers.overrideUserData(jugaad);
        assertEquals(0, allUsers.getUserPosition("jugaad"));
        allUsers.overrideUserData(poop);
        assertEquals(0, allUsers.getUserPosition("jugaad"));
        assertEquals(1, allUsers.getUserPosition("poop"));
        assertEquals(poop, allUsers.getUserOrder(1));
        allUsers.overrideUserData(fatty);
        assertEquals(fatty, allUsers.getUserOrder(1));
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
        assertEquals(jugaad, allUsers.getUserOrder(0));
        allUsers.addUserToDatabase(poop);
        assertEquals(jugaad, allUsers.getUserOrder(0));
        assertEquals(poop, allUsers.getUserOrder(1));
    }



}
