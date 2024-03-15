package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of all the movies ever entered
public class MovieDatabase implements Writable {
    private ArrayList<Movie> allMovies;

    //EFFECTS: constructs an empty list of movies
    public MovieDatabase() {

        allMovies =  new ArrayList<>();
    }


    //EFFECTS: checks if movie is in a list and returns true if it is, if not then return false
    public boolean isMovieInDatabase(Movie movie) {
        for (Movie m : allMovies) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return true;
            }
        }
        return false;
    }


    //EFFECTS: checks if the movie is in database, then returns the database movie
    public Movie isMovieInDatabaseReturnMovie(Movie movie) {
        for (Movie m : allMovies) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return m;
            }
        }
        return null;
    }

    //REQUIRES: movie is in database
    //EFFECTS: prints out details of unrated movie
    public String provideDetailsUnwatched(Movie unwatchedMovie) {
        return unwatchedMovie.movieDetailsUnWatched();
    }

    //REQUIRES: movie not already in database
    //MODIFIES: allMovies
    //EFFECTS: if the movie hasn't already been added, add to allMovies
    public void addMovieToDatabase(Movie m) {
        allMovies.add(m);
    }


    // getters
    public Movie getMovieFromOrderAll(int i) {
        return allMovies.get(i - 1);
    }

    public int getTotalMovies() {
        return allMovies.size();
    }

    public List<Movie> getMovies() {
        return Collections.unmodifiableList(allMovies);
    }


    //MODIFIES: movie.totalRatings
    //EFFECTS: adds rating to movies totalRatings list
    public void addToAverageRating(Movie movie, Review r) {
        isMovieInDatabaseReturnMovie(movie).addReview(r.getUsername(), r);
    }

    //EFFECTS: gets a movie from its title and year, then returns its average rating
    public double getAverageRatingFromDatabase(String movieTitleForSearch, Integer movieYearForSearch) {
        for (Movie m: allMovies) {
            if ((movieTitleForSearch.equals(m.getMovieName())) && (movieYearForSearch == m.getMovieYear())) {
                return m.getAverageRating();
            }
        }
        return 0.0;
    }

    //MODIFIES: JSON
    //EFFECTS: converts entire database to a json object, then returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movies", databaseToJson());
        return json;
    }

    //MODIFIES: JSON
    //EFFECTS: converts each movie in the database to a JSON format and returns the list
    public JSONArray databaseToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Movie m : allMovies) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}

