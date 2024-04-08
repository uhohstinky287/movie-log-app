package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of movies watched by a user
public class User implements Writable {

    private ArrayList<String> friends;
    private ArrayList<Movie> myMovieList;
    private String username;
    private String password;

    //EFFECTS: creates a movie list that is empty
    public User(String username) {
        myMovieList = new ArrayList<>();
        friends = new ArrayList<>();
        this.username = username;
        this.password = "";
    }

    //getters:
    public int getTotalMoviesSeen() {
        return myMovieList.size();
    }

    public Movie getMovieFromOrder(int i) {
        return myMovieList.get(i - 1);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<Movie> getMyMovies() {
        return myMovieList;
    }


    public void setPassword(String password) {
        this.password = password;
        EventLog.getInstance().logEvent(new Event("Password set"));
    }



    //EFFECTS: return true if the movie in myMovieList
    public boolean isMovieInMyMovieList(Movie movie) {
        for (Movie m : myMovieList) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return true;
            }
        }
        return false;
    }


    //REQUIRES: the list is not empty
    //EFFECTS: prints the list of movies in My Movie List
    public String viewMoviesNotEmpty(String username) {
        StringBuilder printedList = new StringBuilder();
        for (Movie m : myMovieList) {
            printedList.append("\n").append(m.getMovieName()).append(
                    "  (").append(m.getMovieYear()).append(")  ").append(m.getUserRating(username)).append("/100");
        }
        return printedList.toString();
    }

    //REQUIRES: list is not empty
    //EFFECTS: prints the list of friends in friend list
    public String viewFriendsNotEmpty() {
        StringBuilder printedFriendList = new StringBuilder();
        for (String s : friends) {
            printedFriendList.append("\n").append(s);
        }
        return printedFriendList.toString();
    }

    //REQUIRES: the movie name and year are different
    //MODIFIES: myMovieList
    //EFFECTS: adds a movie on the personal movie list
    public void addMovie(Movie movie) {
        myMovieList.add(movie);
        EventLog.getInstance().logEvent(new Event(movie.getMovieName() + " added to personal list."));
    }


    //EFFECTS: adds a user to friend list
    public void addFriend(String friend) {
        friends.add(friend);
        EventLog.getInstance().logEvent(new Event(friend + " added as friend"));
    }

    //REQUIRES: friends not empty
    //EFFECTS: removes a friend from friend list
    public void removeFriend(String friend) {
        friends.remove(friend);
        EventLog.getInstance().logEvent(new Event(friend + " removed as friend"));
    }


    //EFFECTS: checks if the movie is in my Movies, then returns the movie from my list
    public Movie isMovieInMyListReturnMovie(Movie movie) {
        for (Movie m : myMovieList) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return m;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("myMovies", myMoviesToJson());
        json.put("friends", friendsToJson());
        return json;
    }

    public JSONArray myMoviesToJson() {
        JSONArray jsonArrayMyMovies = new JSONArray();
        for (Movie m : myMovieList) {
            jsonArrayMyMovies.put(m.toJson());
        }
        return jsonArrayMyMovies;
    }

    public JSONArray friendsToJson() {
        JSONArray jsonArrayFriends = new JSONArray();
        for (String s : friends) {
            jsonArrayFriends.put(s);
        }
        return jsonArrayFriends;
    }

}
