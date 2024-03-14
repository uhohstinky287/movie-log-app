package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Represents a movie with a name, year released, description, director, watched/unwatched status, and user rating
public class Movie implements Writable {
    private String movieName;
    private int movieYear;
    private int userRating;
    private String movieDescription;
    private String director;
    private ArrayList<Integer> totalRatings;
    private Map<String,Review> reviews; //TODO: NEW! create tests

    //EFFECTS: creates a movie with a name, year of release, not watched, 0 rating and
    // no description and undetermined director
    public Movie(String movieName, int movieYear) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.userRating = 0;
        this.movieDescription = "";
        this.director = "";
        totalRatings = new ArrayList<>();
        reviews = new HashMap<>(); //TODO: NEW!
    }

    //getters

    public String getMovieName() {
        return this.movieName;
    }

    public int getMovieYear() {
        return this.movieYear;
    }


    public int getUserRating() {
        return this.userRating;
    }

    public String getMovieDescription() {
        return this.movieDescription;
    }

    public String getDirector() {
        return this.director;
    }

    public Map<String,Review> getReviews() { //TODO:NEW!
        return this.reviews;
    } //TODO:NEW


    //setters

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }


    public void setDirector(String director) {
        this.director = director;
    }

    public void setTotalRatings(ArrayList totalRatings) {
        this.totalRatings = totalRatings;
    }

    public void setReviews(Map reviews) { //TODO: NEW!
        this.reviews = reviews;
    } //TODO:NEW


    //EFFECTS: prints out the details of an unwatched movie
    public String movieDetailsUnWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                    + "Directed by: " + getDirector() + System.lineSeparator()
                    + ratingDetails() + System.lineSeparator()
                    + "Movie Description: " + System.lineSeparator()
                    + getMovieDescription();
    }

    //EFFECTS: Returns user rating or says no ratings yet
    public String ratingDetails() {
        if (getTotalRatings() == 0) {
            return "No ratings yet";
        } else {
            return "Average rating of all users: " + String.valueOf(getAverageRating()) + "/100";
        }
    }

    //EFFECTS: returns the size of totalRatings
    public int getTotalRatings() {
        return totalRatings.size();
    }

    //REQUIRES: totalRatings is not empty
    //EFFECTS: returns the average of the totalRatings
    public double calculateAverageRating() {
        int sum = 0;
        for (int i : totalRatings) {
            sum = sum + i;
        }
        double average = (double) sum / totalRatings.size();
        average = Math.round(average * 10.0) / 10.0;
        return average;
    }

    //EFFECTS: adds a rating to total ratings List
    public void addToTotalRatings(int rating) {
        totalRatings.add(rating);
    }

    //REQUIRES: totalRating != 0
    public double getAverageRating() {
        return calculateAverageRating();
    }

    //EFFECTS: adds a review to reviews list if they have not written one yet
    public void addReview(String username, Review r) {
        if (!reviews.containsKey(username)) {
            reviews.put(username, r);
        }
    } //TODO:NEW

    //EFFECTS: gets a user's review from their username
    public Review getUserReview(String username) {
        if (reviews.containsKey(username)) {
            return reviews.get(username);
        } else  {
            return null;
        }
    } //TODO: NEW

    //EFFECTS: gets a list of all reviews
    public Object[] getAllReviews() {
        return reviews.values().toArray();
    } //TODO: NEW



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", movieName);
        json.put("year", movieYear);
        json.put("rating", userRating);
        json.put("description", movieDescription);
        json.put("director", director);
        json.put("allRatings", totalRatingsToJson());
        return json;
    }

    //EFFECTS: returns totalRatings as a JSON array
    public JSONArray totalRatingsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Integer i : totalRatings) {
            jsonArray.put(i);
        }
        return jsonArray;
    }
}
