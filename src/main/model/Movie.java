package model;


// Represents a movie with a name, year released, description, director, watched/unwatched status, and user rating
public class Movie {
    private String movieName;
    private int movieYear;
    private int userRating;
    private String movieDescription;
    private String director;

    //EFFECTS: creates a movie with a name, year of release, not watched, 0 rating and
    // no description and undetermined director

    public Movie(String movieName, int movieYear) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.userRating = 0;
        this.movieDescription = "";
        this.director = "";
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

    //EFFECTS: prints out movie details
    public String movieDetailsWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                + "Directed by: " + getDirector() + System.lineSeparator()
                + "Your Rating: " + getUserRating() + "/100" + System.lineSeparator()
                + "Movie Description: " + System.lineSeparator()
                + getMovieDescription();
    }



    public String movieDetailsUnWatched() {
        return getMovieName() + "   " + "(" + getMovieYear() + ")" + System.lineSeparator()
                    + "Directed by: " + getDirector() + System.lineSeparator()
                    + "Movie Description: " + System.lineSeparator()
                    + getMovieDescription();
    }

}
