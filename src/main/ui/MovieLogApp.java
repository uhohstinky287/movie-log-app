package ui;

import model.AllMoviesList;
import model.Movie;
import model.MyMovieList;

import java.util.Scanner;

public class MovieLogApp {

    private MyMovieList myMovies;
    private AllMoviesList database;
    private String username;
    private Scanner input;
    private Scanner movieNameInput;
    private Scanner movieYearInput;
    private String movieTitle;
    private Scanner movieDirectorInput;
    private Scanner movieDescriptionInput;
    private Scanner movieRatingInput;
    private int movieYear;



    //EFFECTS: Runs the movie log application
    public MovieLogApp() {
        usernameMenu();
    }


    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init(username);


        while (keepGoing) {
            displayDashBoard();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("e")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you later!");
    }


    //MODIFIES: this
    //EFFECTS: initializes lists
    private void init(String username) {
        myMovies = new MyMovieList(username);
        database = new AllMoviesList();
        input = new Scanner(System.in);
    }


    // EFFECTS: displays the dashboard of options to the user
    private void displayDashBoard() {
        System.out.println("\nWould you like to do?");
        System.out.println("\ta -> Add a movie");
        System.out.println("\ts -> Search for a movie");
        System.out.println("\tv -> View your movies");
        System.out.println("\te -> Exit");
    }


    private void processCommand(String command) {
        if (command.equals("a")) {
            checkIfInMyMoviesForAdd(movieInitializer());
        } else if (command.equals("s")) {
//            searchMovies();
            System.out.println("searching movies");
        } else if (command.equals("v")) {
            viewMyMovies();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //MODIFIES: this.username
    //EFFECTS: entry landing page where the user enters their username, then runs the program
    private void usernameMenu() {
        System.out.println("\nWhat is your username?");
        input = new Scanner(System.in);
        this.username = input.nextLine();
        System.out.println("\nWelcome " + username);
        runApp();
    }

    //EFFECTS: if myMovies is empty, it prints an error and returns to Dashboard, if not then it prints myMovies
    private void viewMyMovies() {
        if (myMovies.getTotalMoviesSeen() == 0) {
            System.out.println("You have not rated any movies yet");
        } else {
            System.out.println(username + "'s Movies:");
            System.out.println("\t");
            System.out.println(myMovies.viewMoviesNotEmpty());
        }
    }


    //EFFECTS: creates and returns a movie with a title and year of release for searching or adding.
    private Movie movieInitializer() {
        System.out.println("What is the title of the movie?");
        movieNameInput = new Scanner(System.in);
        this.movieTitle = movieNameInput.nextLine();
        System.out.println("What year was it released?");
        movieYearInput = new Scanner(System.in);
        this.movieYear = movieYearInput.nextInt();
        Movie movie = new Movie(movieTitle, movieYear);
        return movie;
    }

    //EFFECTS: searches myMovies for the given movie, if myMovies contains the movie, prints and error and returns to
    // dashboard. If the movie is not in myMovies, then it goes to tryAddMovie
    private void checkIfInMyMoviesForAdd(Movie movie) {
        if (myMovies.isMovieInMyMovieList(movie)) {
            System.out.println("You have already added this movie to your list");
        } else {
            tryAddMovie(movie);
        }
    }

    //EFFECTS: searches allMovies for the given movie, if allMovies contains the movie,
    // provides details and the options to add to myMovies. If allMovies doesn't contain the movie,
    // gives option to add to database
    private void tryAddMovie(Movie m) {
        if (database.isMovieInAllList(m)) {
            database.provideDetailsUnwatched(m);
            System.out.println("\t");
            askAddFromDatabase(m);
        } else {
            askAddToDataBase(m);
        }
    }

    //MODIFIES: myMovies,
    //EFFECTS: if the user has seen the movie, asks user for rating, then creates a new movie with details
    // from allMovies plus the user rating and adds to myMovies
    private void askAddFromDatabase(Movie movie) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("Have you seen this movie?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            addFromDataBase(movie);
        }
    }

    //MODIFIES: myMovies
    //EFFECTS: creates a new movie with the same name, title, director and description.
    // Adds a user rating, then adds the movie to myMovies
    private void addFromDataBase(Movie movie) {
        Movie myMovie = new Movie(movieTitle, movieYear);
        myMovie.setDirector(movie.getDirector());
        myMovie.setMovieDescription(movie.getMovieDescription());
        System.out.println("What would you rate this movie from 1-100"); // REQUIRES: the int be between 1-100
        movieRatingInput = new Scanner(System.in);
        myMovie.setUserRating(movieRatingInput.nextInt());
        myMovies.addMovie(myMovie);
    }



    //EFFECTS: if the user types "y" it adds the movie to the database else it returns to DashBoard
    private void askAddToDataBase(Movie movie) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("Would you like to add this movie to the movie database?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            addToDataBase(movie);
        }
    }

    //MODIFIES: allMovies, movie's director, movies description
    //EFFECTS: asks user for movie's director, description, then adds to all movies. Then goes to askAddFromDatabase
    private void addToDataBase(Movie movie) {
        System.out.println("Who directed this movie?");
        movieDirectorInput = new Scanner(System.in);
        movie.setDirector(movieDirectorInput.nextLine());
        System.out.println("Write a one sentence objective description of this movie.");
        movieDescriptionInput = new Scanner(System.in);
        movie.setMovieDescription(movieDescriptionInput.nextLine());
        database.addMovieToDatabase(movie);
        askAddFromDatabase(movie);
    }


}

