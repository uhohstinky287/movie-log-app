package model;


import java.util.ArrayList;

// Represents a list of all the movies ever entered
public class AllMoviesList {
    private ArrayList<Movie> allMovies;

    //EFFECTS: constructs an empty list of movies
    public AllMoviesList() {
        allMovies =  new ArrayList<>();
    }


    public int getTotalMovies() {
        return allMovies.size();
    }


    //EFFECTS: checks if movie is in a list
    public boolean isMovieInAllList(Movie movie) {
        for (Movie m : allMovies) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: prints out details of unrated movie
    public void provideDetailsUnwatched(Movie unwatchedMovie) {
        unwatchedMovie.movieDetailsUnWatched();
    }

    //REQUIRES:
    //MODIFIES: allMovies
    //EFFECTS: if the movie hasn't already been added, add to allMovies
    public void addMovieToDatabase(Movie m) {
        allMovies.add(m);
    }


    //Just for testing
    public Movie getMovieFromOrderAll(int i) {
        return allMovies.get(i - 1);
    }
}
