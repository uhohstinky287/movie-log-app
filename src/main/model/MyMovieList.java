package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of movies watched by a user
public class MyMovieList implements Writable {

    private ArrayList<Movie> myMovieList;
    private String username;
    private String password;

    //EFFECTS: creates a movie list that is empty
    public MyMovieList(String username) {
        myMovieList = new ArrayList<>();
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

    //setters:
//    public void setMyMovieList(ArrayList myMovieList) {
//        this.myMovieList = myMovieList;
//    }

    public void setPassword(String password) {
        this.password = password;
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

    //REQUIRES: the movie name and year are different
    //MODIFIES: myMovieList
    //EFFECTS: adds a movie on the personal movie list
    public void addMovie(Movie movie) {
        myMovieList.add(movie);
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
        return json;
    }

    public JSONArray myMoviesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Movie m : myMovieList) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }

}
