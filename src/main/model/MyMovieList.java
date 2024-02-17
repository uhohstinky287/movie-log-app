package model;


import java.util.ArrayList;

// Represents a list of movies watched by a user
public class MyMovieList extends AllMoviesList {

    private ArrayList<Movie> myMovieList;
    private String username;

    //EFFECTS: creates a movie list that is empty
    public MyMovieList(String username) {
        myMovieList = new ArrayList<Movie>();
        this.username = username;
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


    //EFFECTS: return true if the movie in myMovieList
    public boolean isMovieInMyMovieList(Movie movie) {
        for (Movie m : myMovieList) {
            if ((movie.getMovieName().equals(m.getMovieName())) && (movie.getMovieYear() == m.getMovieYear())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: prints the list of movies in My Movie List
    public String viewMoviesNotEmpty() {
        String printedList = "";
        for (Movie m : myMovieList) {
            printedList += "\n" + m.getMovieName()
                        + "  (" + m.getMovieYear() + ")  " + m.getUserRating() + "/100";
        }
        return printedList;
    }

    //REQUIRES: the movie name and year be different
    //MODIFIES: myMovieList
    //EFFECTS: adds a movie on the personal movie list
    public void addMovie(Movie movie) {
        myMovieList.add(movie);
    }

}
