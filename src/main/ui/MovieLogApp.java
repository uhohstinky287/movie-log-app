package ui;

import model.*;
import persistence.JsonReaderMovieDatabase;
import persistence.JsonReaderUserDataStorage;
import persistence.JsonWriterMovieDatabase;
import persistence.JsonWriterUserDataStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MovieLogApp {
    private static final String JSON_MOVIES = "./data/movieDatabase.json";
    private static final String JSON_USERS = "./data/userDataStorage.json";
    private MyMovieList myMovies;
    private MovieDatabase database;
    private UserDataStorage allUsers;
    private String username;
    private String password;
    private String usernameChecker;
    private Scanner input;
    private Scanner movieNameInput;
    private Scanner movieYearInput;
    private String movieTitle;
    private Scanner movieDirectorInput;
    private Scanner movieDescriptionInput;
    private Scanner movieRatingInput;
    private Scanner otherUserInput;
    private String otherUser;
    private int movieYear;
    private int movieRating;
    private Review review;
    private JsonWriterMovieDatabase jsonWriterMovieDatabase;
    private JsonReaderMovieDatabase jsonReaderMovieDatabase;
    private JsonWriterUserDataStorage jsonWriterUserDataStorage;
    private JsonReaderUserDataStorage jsonReaderUserDataStorage;


    //EFFECTS: Runs the movie log application
    public MovieLogApp() throws FileNotFoundException {
        database = new MovieDatabase();
        allUsers = new UserDataStorage();
        jsonWriterMovieDatabase = new JsonWriterMovieDatabase(JSON_MOVIES);
        jsonReaderMovieDatabase = new JsonReaderMovieDatabase(JSON_MOVIES);
        jsonWriterUserDataStorage = new JsonWriterUserDataStorage(JSON_USERS);
        jsonReaderUserDataStorage = new JsonReaderUserDataStorage(JSON_USERS);
        loadDatabase();
        loadUsers();
        startingMenu();
    }


    private void runApp() {
        boolean keepGoing = true;
        String command;
        init(username);
        while (keepGoing) {
            displayDashBoard();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("e")) {
//                allUsers.overrideUserData(myMovies);
                displaySaveOption();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you later!");
        System.exit(0);
    }


    //MODIFIES: this
    //EFFECTS: initializes lists
    private void init(String username) {
        input = new Scanner(System.in);
    }


    // EFFECTS: displays the dashboard of options to the user
    private void displayDashBoard() {
        System.out.println("\n" + username + "'s Movie Log");
        System.out.println("\nWould you like to do?");
        System.out.println("\ta -> Add a movie");
        System.out.println("\ts -> Search for a movie");
        System.out.println("\tu -> Search for another user");
        System.out.println("\tv -> View your movies");
//        System.out.println("\tl -> Logout of your account");
        System.out.println("\te -> Exit App and logout");
    }

    // processes dashboard commands
    private void processCommand(String command) {
        if (command.equals("a")) {
            checkIfInMyMoviesForAdd(movieInitializer());
        } else if (command.equals("s")) {
            checkIfInMyMoviesForSearch(movieInitializer());
        } else if (command.equals("u")) {
            searchUsers();
            System.out.println("searching users...");
        } else if (command.equals("v")) {
            viewMyMovies();
//        } else if (command.equals("l")) {
//            allUsers.overrideUserData(myMovies);
//            displaySaveOption();
//            System.out.println("You have been logged out");
//            startingMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //EFFECTS: creates a starting menu
    private void startingMenu() {
        String selection = "";
        input = new Scanner(System.in);
        while (!(selection.equals("c") || selection.equals("l"))) {
            System.out.println("\n\n");
            System.out.println("MOVIE LOG APP");
            System.out.println("\n\nl -> login and load my movies");
            System.out.println("c -> create a account (new user)");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("l")) {
            loginMenu();
        } else {
            System.out.println("Welcome!");
            System.out.println("Lets get started");
            createAccountMenu();
        }
    }

    //MODIFIES: this.username
    //EFFECTS: entry landing page where the user enters their username, then runs the program
    private void loginMenu() {
        System.out.println("\n\nLOGIN");
        System.out.println("What is your username?");
        input = new Scanner(System.in);
        this.username = input.nextLine();
        username = username.toLowerCase();
        if (allUsers.isUserInDatabase(username)) {
            returningUser();
        } else {
            System.out.println("Username not in database\n\n");
            startingMenu();
        }
        runApp();
    }

    //MODIFIES: this.password
    //EFFECTS: provides the menu for a returning user
    private void returningUser() {
        System.out.println("Password:");
        input = new Scanner(System.in);
        this.password = input.nextLine();
        if (allUsers.isUserInDatabaseReturnUser(username).getPassword().equals(password)) {
            myMovies = allUsers.isUserInDatabaseReturnUser(username);
            System.out.println("\n\nWelcome back " + username + " to:");
        } else {
            System.out.println("\n\n\nIncorrect password");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            startingMenu();
        }
    }


    //MODIFIES: this.username, this.password, this.usernameChecker
    //EFFECTS:creates a page for creating an account
    private void createAccountMenu() {
        System.out.println("\nPick a username:");
        input = new Scanner(System.in);
        this.usernameChecker = input.nextLine();
        if (!allUsers.isUserInDatabase(usernameChecker)) {
            this.username = usernameChecker;
            System.out.println("Create a password:");
            input = new Scanner(System.in);
            this.password = input.nextLine();
            myMovies = new MyMovieList(username);
            myMovies.setPassword(password);
            System.out.println("\nWelcome " + username + " to:");
        } else {
            System.out.println("This username is taken");
            createAccountMenu();
        }
        runApp();
    }




    //EFFECTS: if myMovies is empty, it prints an error and returns to Dashboard, if not then it prints myMovies
    private void viewMyMovies() {
        if (myMovies.getTotalMoviesSeen() == 0) {
            System.out.println("You have not rated any movies yet");
            returnToMenuOption();
        } else {
            System.out.println(username + "'s Movies:");
            System.out.println("\t");
            System.out.println(myMovies.viewMoviesNotEmpty(username));
            returnToMenuOption();
        }
    }

    //EFFECTS: searches all users and produces the users movie list
    private void searchUsers() {
        System.out.println("\nWhat is the other user's username?");
        otherUserInput = new Scanner(System.in);
        this.otherUser = otherUserInput.nextLine();
        if (allUsers.isUserInDatabase(otherUser)) {
            if (allUsers.isUserInDatabaseReturnUser(otherUser).getTotalMoviesSeen() == 0) {
                System.out.println(allUsers.isUserInDatabaseReturnUser(otherUser).getUsername()
                        + " has not rated any movies yet");
                returnToMenuOption();
            } else {
                System.out.println(otherUser + "'s Movies:");
                System.out.println("\t");
                System.out.println(allUsers.isUserInDatabaseReturnUser(otherUser).viewMoviesNotEmpty(username));
                returnToMenuOption();
            }
        } else {
            System.out.println("This user does not exist");
            returnToMenuOption();
        }
    }


    //EFFECTS: creates and returns a movie with a title and year of release for searching or adding.
    private Movie movieInitializer() {
        System.out.println("What is the title of the movie?");
        movieNameInput = new Scanner(System.in);
        this.movieTitle = movieNameInput.nextLine();
        movieTitle = movieTitle.toUpperCase();
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
            returnToMenuOption();
        } else {
            tryAddMovie(movie);
        }
    }

    //EFFECTS: searches for movie in myMovies
    private void checkIfInMyMoviesForSearch(Movie movie) {
        if (myMovies.isMovieInMyMovieList(movie)) {
            System.out.println("\n");
            System.out.println(watchedMovieDetails(movie));
            returnToMenuOption();
        } else {
            tryAddMovie(movie);
        }
    }

    //EFFECTS: searches allMovies for the given movie, if allMovies contains the movie,
    // provides details and the options to add to myMovies. If allMovies doesn't contain the movie,
    // gives option to add to database
    private void tryAddMovie(Movie m) {
        if (database.isMovieInDatabase(m)) {
            System.out.println("\n");
            System.out.println(database.provideDetailsUnwatched(database.isMovieInDatabaseReturnMovie(m)));
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
            System.out.println("\ty -> Yes and rate");
            System.out.println("\tn -> No and return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            addFromDataBase(movie);
        } else {
            System.out.println("Returning to menu");
            System.out.println("\n");
            System.out.println("\n");
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
        movieRating = movieRatingInput.nextInt();
        review = new Review(username, movieRating);
        database.addToAverageRating(myMovie, review);
        myMovies.addMovie(database.isMovieInDatabaseReturnMovie(myMovie));
        System.out.println("\n" + movieTitle + " has been added to your list");
        returnToMenuOption();
    }


    //EFFECTS: if the user types "y" it adds the movie to the database else it returns to DashBoard
    private void askAddToDataBase(Movie movie) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("r"))) {
            System.out.println("\nThis movie is not in our database");
            System.out.println("");
            System.out.println("Would you like to add this movie to the movie database?");
            System.out.println("\ty -> Yes");
            System.out.println("\tr -> Return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            addToDataBase(movie);
        } else {
            System.out.println("Returning to menu");
            System.out.println("\n");
            System.out.println("\n");
        }
    }

    //MODIFIES: allMovies, movie's director, movies description
    //EFFECTS: asks user for movie's director, description, then adds to all movies. Then goes to askAddFromDatabase
    private void addToDataBase(Movie movie) {
        System.out.println("\nWho directed this movie?");
        movieDirectorInput = new Scanner(System.in);
        movie.setDirector(movieDirectorInput.nextLine());
        System.out.println("Write a one sentence objective description of this movie.");
        movieDescriptionInput = new Scanner(System.in);
        movie.setMovieDescription(movieDescriptionInput.nextLine());
        database.addMovieToDatabase(movie);
        askAddFromDatabase(movie);
    }


    //EFFECTS: gives a screen to return user to the dashboard
    private void returnToMenuOption() {
        String selection = "";
        while (!(selection.equals("r"))) {
            System.out.println("");
            System.out.println("");
            System.out.println("Press r to return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        System.out.println("Returning to menu");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

    }

    //EFFECTS: Provides the details of a movie that is in myMovies
    private String watchedMovieDetails(Movie movie) {
        return myMovies.isMovieInMyListReturnMovie(movie).getMovieName() + "   " + "("
                + myMovies.isMovieInMyListReturnMovie(movie).getMovieYear() + ")" + System.lineSeparator()
            + "Directed by: " + database.isMovieInDatabaseReturnMovie(movie).getDirector() + System.lineSeparator()
            + "Your Rating: " + myMovies.isMovieInMyListReturnMovie(movie).getUserRating(username) + "/100"
                + "  ...  Average rating of all users: "
            + database.getAverageRatingFromDatabase(movie.getMovieName(), movie.getMovieYear())
                + "/100" + System.lineSeparator()
            + "Movie Description: " + System.lineSeparator()
            + database.isMovieInDatabaseReturnMovie(movie).getMovieDescription();
    }


    //EFFECTS: creates a display that prompts the user to choose whether to save the data to JSOn
    private void displaySaveOption() {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("Do you want to save your changes?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            allUsers.overrideUserData(myMovies);
            save();
        }
    }

    //EFFECTS: saves all data to JSON files
    private void save() {
        try {
            jsonWriterMovieDatabase.open();
            jsonWriterMovieDatabase.write(database);
            jsonWriterMovieDatabase.close();
            System.out.println("Saved " + " to " + JSON_MOVIES);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_MOVIES);
        }
        try {
            jsonWriterUserDataStorage.open();
            jsonWriterUserDataStorage.write(allUsers);
            jsonWriterUserDataStorage.close();
            System.out.println("Saved " + " to " + JSON_USERS);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_USERS);
        }
    }



    // MODIFIES: this
    // EFFECTS: loads database from file
    private void loadDatabase() {
        try {
            database = jsonReaderMovieDatabase.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MOVIES);
        }
    }

    //EFFECTS: loads the list of all users from Json
    private void loadUsers() {
        try {
            allUsers = jsonReaderUserDataStorage.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_USERS);
        }
    }


    //todo from my movie list, see full description
}

