package model;


import java.util.ArrayList;

// Represents a list of all the movies ever entered
public class AllMoviesList {
    private ArrayList<Movie> allMovies;

    //EFFECTS: constructs an empty list of movies
    public AllMoviesList() {
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


    //Just for testing
    public Movie getMovieFromOrderAll(int i) {
        return allMovies.get(i - 1);
    }

    public int getTotalMovies() {
        return allMovies.size();
    }

    public void addToAverageRating(Movie movie, Integer rating) {
        isMovieInDatabaseReturnMovie(movie).addToTotalRatings(rating);
    }

    public double getAverageRatingFromDatabase(String movieTitleForSearch, Integer movieYearForSearch) {
        for (Movie m: allMovies) {
            if ((movieTitleForSearch.equals(m.getMovieName())) && (movieYearForSearch == m.getMovieYear())) {
                return m.getAverageRating();
            }
        }
        return 0.0;
    }
}

