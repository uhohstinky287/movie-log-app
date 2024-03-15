package model;


import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a movie with a name, year released, description, director, watched/unwatched status, and user rating
public class Movie implements Writable {
    private String movieName;
    private int movieYear;
//    private int userRating;
    private String movieDescription;
    private String director;
    private Map<String,Review> reviews; //TODO: NEW! create tests


    //EFFECTS: creates a movie with a name, year of release, not watched, 0 rating and
    // no description and undetermined director
    public Movie(String movieName, int movieYear) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieDescription = "";
        this.director = "";
        reviews = new LinkedHashMap<>();
    }

    //getters

    public String getMovieName() {
        return this.movieName;
    }

    public int getMovieYear() {
        return this.movieYear;
    }


    public int getUserRating(String username) {
        return reviews.get(username).getRating();
    }

    public String getMovieDescription() {
        return this.movieDescription;
    }

    public String getDirector() {
        return this.director;
    }

    //REQUIRES: totalRating != 0
    //EFFECTS: calculates the average rating of the hashmap, then returns it
    public double getAverageRating() {
        int sum = 0;
        for (int i : getTotalRatingsList()) {
            sum = sum + i;
        }
        double average = (double) sum / getTotalRatingsSize();
        average = Math.round(average * 10.0) / 10.0;
        return average;
    }

    public Map<String,Review> getReviewsMap() { //TODO:NEW!
        return this.reviews;
    }

    //setters

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }


    public void setDirector(String director) {
        this.director = director;
    }


    public void setReviews(Map reviews) { //TODO: NEW!
        this.reviews = reviews;
    }




    //EFFECTS: prints out the details of an unwatched movie
    public String movieDetailsUnWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                    + "Directed by: " + getDirector() + System.lineSeparator()
                    + ratingDetails() + System.lineSeparator()
                    + "Movie Description: " + System.lineSeparator()
                    + getMovieDescription();
    }

    //EFFECTS: Provides the details of a movie that is in myMovies
    public String movieDetailsWatched(String username) {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                + "Directed by: " + getDirector() + System.lineSeparator()
                + "Your Rating: " + getUserRating(username) + "/100"
                + "  ...  Average rating of all users: " + getAverageRating() + "/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + getMovieDescription();
    }

    //EFFECTS: Returns user rating or says no ratings yet
    public String ratingDetails() {
        if (reviews.isEmpty()) {
            return "No ratings yet";
        } else {
            return "Average rating of all users: " + String.valueOf(getAverageRating()) + "/100";
        }
    }


    //EFFECTS: makes a list of all the ratings left in the reviews
    public List<Integer> getTotalRatingsList() {
        List<Integer> totalRatingsLOL = new ArrayList<>();
        for (Review r : getAllReviews()) {
            totalRatingsLOL.add(r.getRating());
        }
        return totalRatingsLOL;
    }

    public int getTotalRatingsSize() {
        return getTotalRatingsList().size();
    }


    //EFFECTS: adds a review to reviews list if they have not written one yet
    public void addReview(String username, Review r) {
        if (!reviews.containsKey(username)) {
            reviews.put(username, r);
        }
    } //TODO:NEW

    //REQUIRES: user in the list
    //EFFECTS: changes the user's review
    public void changeReview(String username, Review r) {
        reviews.replace(username, r);
    }

    //EFFECTS: gets a user's review from their username
    public Review getUserReview(String username) {
        if (reviews.containsKey(username)) {
            return reviews.get(username);
        } else  {
            return null;
        }
    } //TODO: NEW

    //EFFECTS: gets a list of all reviews
    public List<Review> getAllReviews() {
        List<Review> reviewsAsList = new ArrayList<Review>(reviews.values());
        return reviewsAsList;
    } //TODO: NEW


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", movieName);
        json.put("year", movieYear);
        json.put("description", movieDescription);
        json.put("director", director);
        json.put("reviews", reviewsToJson());
        return json;
    }

    //EFFECTS: returns reviews as an Object that JSON can read
    public JSONObject reviewsToJson() {
        JSONObject jsonHashMap = new JSONObject();
        for (String key : reviews.keySet()) {
            jsonHashMap.put(key, reviews.get(key).toJson());
        }
        return jsonHashMap;
    }

}
