package model;


import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User myMovies;
    private Movie batman;
    private Movie barbie;
    private Movie batmanRated;
    private Movie oppenheimer;
    private Movie barbieOld;
    private JSONArray myMoviesJSON;
    private JSONArray myFriendsJSON;
    Review r1;

    @BeforeEach
    public void setUp() {
        myMovies = new User("jugs");
        batman = new Movie("The Batman", 2022);
        barbie = new Movie("Barbie", 2023);
        oppenheimer = new Movie("Oppenheimer", 2023);
        barbieOld = new Movie("Barbie", 2001);
        batmanRated = new Movie("The Batman", 2022);
        r1 = new Review("jugaad", 100);
        batmanRated.addReview(r1.getUsername(), r1);
        batmanRated.setMovieDescription("Emo Batman");
        batmanRated.setDirector("Matt Reeves");
        myMoviesJSON = new JSONArray();
        myFriendsJSON = new JSONArray();
    }

    //tests the constructor
    @Test
    public void testMyMovieList() {
        assertEquals(0, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testGetTotalMoviesSeen() {
        assertEquals(0, myMovies.getTotalMoviesSeen());
        myMovies.addMovie(batman);
        assertEquals(1, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testGetMovieFromOrder() {
        myMovies.addMovie(batman);
        assertEquals(batman, myMovies.getMovieFromOrder(1));
    }

    @Test
    public void testGetUserName() {
        assertEquals("jugs", myMovies.getUsername());
    }

    @Test
    public void testGetPassword() {
        myMovies.setPassword("jugaad");
        assertEquals("jugaad", myMovies.getPassword());
    }

    @Test
    public void testGetFriends() {
        assertTrue(myMovies.getFriends().isEmpty());
        myMovies.addFriend("poop");
        assertEquals("poop", myMovies.getFriends().get(0));

    }

    @Test
    public void testIsMovieInMyMovieList() {
        assertFalse(myMovies.isMovieInMyMovieList(batman));
        myMovies.addMovie(batman);
        assertFalse(myMovies.isMovieInMyMovieList(barbie));
        assertTrue(myMovies.isMovieInMyMovieList(batman));
        myMovies.addMovie(barbie);
        assertTrue(myMovies.isMovieInMyMovieList(batman));
        assertTrue(myMovies.isMovieInMyMovieList(barbie));
        assertFalse(myMovies.isMovieInMyMovieList(barbieOld));
        assertFalse(myMovies.isMovieInMyMovieList(oppenheimer));
    }

    @Test
    public void testViewMoviesNotEmpty() {
        myMovies.addMovie(batmanRated);
        assertEquals("\nThe Batman  (2022)  100/100", myMovies.viewMoviesNotEmpty(r1.getUsername()));
    }

    @Test
    public void testViewFriendsNotEmpty() {
        myMovies.addFriend("poop");
        assertEquals("\npoop", myMovies.viewFriendsNotEmpty());
    }


    @Test
    public void testAddMovie() {
        myMovies.addMovie(barbie);
        assertEquals(1, myMovies.getTotalMoviesSeen());
        myMovies.addMovie(batman);
        assertEquals(2, myMovies.getTotalMoviesSeen());
    }

    @Test
    public void testAddFriend() {
        assertTrue(myMovies.getFriends().isEmpty());
        myMovies.addFriend("poop");
        assertEquals("poop", myMovies.getFriends().get(0));
        myMovies.addFriend("butt");
        assertEquals("poop", myMovies.getFriends().get(0));
        assertEquals("butt", myMovies.getFriends().get(1));

    }



    @Test
    public void testIsMovieInMyListReturnMovie() {
        assertNull(myMovies.isMovieInMyListReturnMovie(batmanRated));
        myMovies.addMovie(batmanRated);
        assertEquals(batmanRated, myMovies.isMovieInMyListReturnMovie(batman));
        assertNull(myMovies.isMovieInMyListReturnMovie(barbie));
        myMovies.addMovie(barbie);
        assertEquals(barbie, myMovies.isMovieInMyListReturnMovie(barbie));
        assertNull(myMovies.isMovieInMyListReturnMovie(barbieOld));
        assertNull(myMovies.isMovieInMyListReturnMovie(oppenheimer));
    }


    @Test
    public void testToJson() {
        myMovies.addMovie(batmanRated);
        myMovies.setPassword("fart");
        JSONObject toJSonExpected = new JSONObject();
        toJSonExpected.put("username", "jugs");
        toJSonExpected.put("password", "fart");
        toJSonExpected.put("myMovies", myMovies.myMoviesToJson());
        toJSonExpected.put("friends", new ArrayList<>());

        assertEquals(toJSonExpected.toString(), myMovies.toJson().toString());
    }

    @Test
    public void testMyMoviesToJson() {
        assertEquals(myMoviesJSON.toList(), myMovies.myMoviesToJson().toList());
        myMovies.addMovie(batman);
        myMoviesJSON.put(batman.toJson());
        assertEquals(myMoviesJSON.toList(), myMovies.myMoviesToJson().toList());
    }

    @Test
    public void testMyFriendsToJson() {
        assertEquals(myFriendsJSON.toList(), myMovies.friendsToJson().toList());
        myMovies.addFriend("poop");
        myFriendsJSON.put("poop");
        assertEquals(myFriendsJSON.toList(), myMovies.friendsToJson().toList());
    }


}
