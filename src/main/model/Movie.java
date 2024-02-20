package model;


import java.util.ArrayList;

// Represents a movie with a name, year released, description, director, watched/unwatched status, and user rating
public class Movie {
    private String movieName;
    private int movieYear;
    private int userRating;
    private String movieDescription;
    private String director;
    private ArrayList<Integer> totalRatings;

    //EFFECTS: creates a movie with a name, year of release, not watched, 0 rating and
    // no description and undetermined director
    public Movie(String movieName, int movieYear) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.userRating = 0;
        this.movieDescription = "";
        this.director = "";
        totalRatings = new ArrayList<>();
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

    //EFFECTS: prints out the movie details of a watched movie
    public String movieDetailsWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                + "Directed by: " + getDirector() + System.lineSeparator()
                + "Your Rating: " + getUserRating() + "/100" + "  ...  Average rating of all users: "
//                + String.valueOf(allMoviesList.getAverageRatingFromDatabase(getMovieName(), getMovieYear()))
                + "/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + getMovieDescription();
    }


    //EFFECTS: prints out the details of an unwatched movie
    public String movieDetailsUnWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                    + "Directed by: " + getDirector() + System.lineSeparator()
                    + ratingDetails() + System.lineSeparator()
                    + "Movie Description: " + System.lineSeparator()
                    + getMovieDescription();
    }

    //EFFECTS: Returns user rating or says no ratings yet
    public String ratingDetails() { //TODO: TEST
        if (getTotalRatings() == 0) {
            return "No ratings yet";
        } else {
            return "Average rating of all users: " + String.valueOf(getAverageRating()) + "/100";
        }
    }

    //EFFECTS: returns the size of totalRatings
    public int getTotalRatings() { //TODO: TEST
        return totalRatings.size();
    }

    //REQUIRES: totalRatings is not empty
    //EFFECTS: returns the average of the totalRatings
    public double calculateAverageRating() { //TODO: TEST (copy getAverageRating)
        int sum = 0;
        for (int i : totalRatings) {
            sum = sum + i;
        }
        double average = (double) sum / totalRatings.size();
        average = Math.round(average * 10.0) / 10.0;
        return average;
    }

    //EFFECTS: adds a rating to total ratings List
    public void addToTotalRatings(int rating) { //TODO: TEST
        totalRatings.add(rating);
    }

    //REQUIRES: totalRating != 0
    public double getAverageRating() {
        return calculateAverageRating();
    }



}
