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

    private User user;
    private MovieDatabase database;
    private UserDataStorage allUsers;
    private String username;
    private String password;
    private Scanner input;
    private String movieTitle;
    private String otherUser;
    private int movieYear;
    private int movieRating;
    private Review review;




    //EFFECTS: Runs the movie log application
    public MovieLogApp() throws FileNotFoundException {

        database = new MovieDatabase();
        allUsers = new UserDataStorage();
        loadDatabase();
        loadUsers();
    }



    // MODIFIES: this
    // EFFECTS: loads database from file
    private void loadDatabase() {
        JsonReaderMovieDatabase jsonReaderMovieDatabase = new JsonReaderMovieDatabase(JSON_MOVIES);
        try {
            database = jsonReaderMovieDatabase.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MOVIES);
        }
    }

    //EFFECTS: loads the list of all users from Json
    private void loadUsers() {
        JsonReaderUserDataStorage jsonReaderUserDataStorage = new JsonReaderUserDataStorage(JSON_USERS);
        try {
            allUsers = jsonReaderUserDataStorage.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_USERS);
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
        if (allUsers.isUserInDataBase(username)) {
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
        if (allUsers.returnUserFromDatabase(username).getPassword().equals(password)) {
            user = allUsers.returnUserFromDatabase(username);
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
        String usernameChecker = input.nextLine();
        if (!allUsers.isUserInDataBase(usernameChecker)) {
            this.username = usernameChecker;
            System.out.println("Create a password:");
            input = new Scanner(System.in);
            this.password = input.nextLine();
            user = new User(username);
            user.setPassword(password);
            System.out.println("\nWelcome " + username + " to:");
        } else {
            System.out.println("This username is taken");
            createAccountMenu();
        }
        runApp();
    }

    private void runApp() {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            displayDashBoard();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("e")) {
                displaySaveOption();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you later!");
        System.exit(0);
    }



    // EFFECTS: displays the dashboard of options to the user
    private void displayDashBoard() {
        System.out.println("\n" + username + "'s Movie Log");
        System.out.println("\nWould you like to do?");
        System.out.println("\ta -> Add a movie");
        System.out.println("\ts -> Search for a movie");
        System.out.println("\tf -> View your friends");
        System.out.println("\tu -> Search for another user");
        System.out.println("\tv -> View your movies");
//        System.out.println("\tl -> Logout of your account");
        System.out.println("\te -> Exit App and logout");
    }

    // processes dashboard commands
    private void processCommand(String command) {
        switch (command) {
            case "a":
                checkIfInMyMoviesForAdd(movieInitializer());
                break;
            case "s":
                checkIfInMyMoviesForSearch(movieInitializer());
                break;
            case "f":
                viewFriends();
                break;
            case "u":
                searchUsers();
                break;
            case "v":
                viewMyMovies();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    //EFFECTS: creates and returns a movie with a title and year of release for searching or adding.
    private Movie movieInitializer() {
        System.out.println("What is the title of the movie?");
        input = new Scanner(System.in);
        this.movieTitle = input.nextLine();
        movieTitle = movieTitle.toUpperCase();
        System.out.println("What year was it released?");
        input = new Scanner(System.in);
        this.movieYear = input.nextInt();
        return new Movie(movieTitle, movieYear);
    }

    //EFFECTS: checks if a user is in a user's friend list
    private void viewFriends() {
        if (user.getFriends().isEmpty()) {
            System.out.println("\n You have no friends :(");
            returnToMenuOption();
        } else {
            System.out.println("Your friends:");
            System.out.println("\t");
            System.out.println(user.viewFriendsNotEmpty());
            returnToMenuOption();
        }
    }

    //EFFECTS: searches all users and produces the users movie list
    private void searchUsers() {
        System.out.println("\nWhat is the other user's username?");
        input = new Scanner(System.in);
        this.otherUser = input.nextLine();
        System.out.println("\nsearching users...\n");
        if (allUsers.isUserInDataBase(otherUser)) {
            if (allUsers.returnUserFromDatabase(otherUser).getTotalMoviesSeen() == 0) {
                System.out.println(allUsers.returnUserFromDatabase(otherUser).getUsername()
                        + " has not rated any movies yet");
                askAddFriend(otherUser);
            } else {
                System.out.println(otherUser + "'s Movies:");
                System.out.println("\t");
                System.out.println(allUsers.returnUserFromDatabase(otherUser).viewMoviesNotEmpty(otherUser));
                askAddFriend(otherUser);
            }
        } else {
            System.out.println("This user does not exist");
            returnToMenuOption();
        }
    }

    //EFFECTS: asks user if they want to add a user as a friend
    private void askAddFriend(String otherUser) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\n Do you want to add user as a friend?");
            System.out.println("\ty -> Yes ");
            System.out.println("\tn -> No and return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            user.addFriend(otherUser);
        } else {
            System.out.println("Returning to menu");
            System.out.println("\n");
            System.out.println("\n");
        }
    }

    //EFFECTS: if User's movie List is empty, it prints an error and returns to Dashboard,
    // if not then it prints user's movie List
    private void viewMyMovies() {
        if (user.getTotalMoviesSeen() == 0) {
            System.out.println("You have not rated any movies yet");
            returnToMenuOption();
        } else {
            System.out.println(username + "'s Movies:");
            System.out.println("\t");
            System.out.println(user.viewMoviesNotEmpty(username));
            returnToMenuOption();
        }
    }




    //EFFECTS: searches user's movie list for the given movie, if it contains the movie, prints and error and returns to
    // dashboard. If the movie is not in user's movies, then it goes to tryAddMovie
    private void checkIfInMyMoviesForAdd(Movie movie) {
        if (user.isMovieInMyMovieList(movie)) {
            System.out.println("You have already added this movie to your list");
            returnToMenuOption();
        } else {
            tryAddMovie(movie);
        }
    }

    //EFFECTS: searches for movie in user's movies
    private void checkIfInMyMoviesForSearch(Movie movie) {
        if (user.isMovieInMyMovieList(movie)) {
            System.out.println("\n");
            System.out.println(database.returnMovieFromDatabase(movie).movieDetailsWatched(username));
            returnToMenuOption();
        } else {
            tryAddMovie(movie);
        }
    }

    //EFFECTS: searches allMovies for the given movie, if allMovies contains the movie,
    // provides details and the options to add to user's movies. If allMovies doesn't contain the movie,
    // gives option to add to database
    private void tryAddMovie(Movie m) {
        if (database.isMovieInDatabase(m)) {
            System.out.println("\n");
            System.out.println(database.provideDetailsUnwatched(database.returnMovieFromDatabase(m)));
            System.out.println("\t");
            askAddFromDatabase(m);
        } else {
            askAddToDataBase(m);
        }
    }

    //MODIFIES: user,
    //EFFECTS: if the user has seen the movie, if yes, go to addFromDatabase, if not, returns to dashboard
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

    //MODIFIES: user
    //EFFECTS: Asks user for rating, then creates a new review. Adds review to movie, and adds movie to list
    // then goes to returnToMenuOption
    private void addFromDataBase(Movie movie) {
        System.out.println("What would you rate this movie from 1-100"); // REQUIRES: the int be between 1-100
        input = new Scanner(System.in);
        movieRating = input.nextInt();
        review = new Review(username, movieRating);
        askAddWrittenReview(movie, review);
        returnToMenuOption();
    }

    //EFFECTS: Asks for a written review, if they want to write a review, then goes to addWrittenReview, if not
    // then leaves review blank
    private void askAddWrittenReview(Movie movie, Review review) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\nWould you like to write a review?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            addWrittenReview(movie, review);
        } else {
            database.addToAverageRating(movie, review);
            user.addMovie(database.returnMovieFromDatabase(movie));
            System.out.println("\n" + movieTitle + " has been added to your list");
        }
    }

    //EFFECTS: allows user to write written review
    private void addWrittenReview(Movie movie, Review review) {
        System.out.println("What are your thoughts on " + database.returnMovieFromDatabase(movie).getDirector()
                + "'s " + movie.getMovieName() + "?");
        input = new Scanner(System.in);
        String movieWrittenReview = input.nextLine();
        review.setWrittenReview(movieWrittenReview);
        System.out.println("\n Thank you for your review");
        database.addToAverageRating(movie, review);
        user.addMovie(database.returnMovieFromDatabase(movie));
        System.out.println("\n" + movieTitle + " has been added to your list");
    }


    //EFFECTS: if the user types "y" it adds the movie to the database else it returns to DashBoard
    private void askAddToDataBase(Movie movie) {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("r"))) {
            System.out.println("\nThis movie is not in our database\n");
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
        input = new Scanner(System.in);
        movie.setDirector(input.nextLine());
        System.out.println("Write a one sentence objective description of this movie.");
        input = new Scanner(System.in);
        movie.setMovieDescription(input.nextLine());
        database.addMovieToDatabase(movie);
        askAddFromDatabase(movie);
    }


    //EFFECTS: gives a screen to return user to the dashboard
    private void returnToMenuOption() {
        String selection = "";
        while (!(selection.equals("r"))) {
            System.out.println("\n\nPress r to return to menu");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        System.out.println("Returning to menu");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");

    }


    //EFFECTS: creates a display that prompts the user to choose whether to save the data to JSOn
    public void displaySaveOption() {
        String selection = "";
        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("Do you want to save your changes?");
            System.out.println("\ty -> Yes");
            System.out.println("\tn -> No");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            allUsers.overrideUserData(user);
            save();
        }
    }

    //EFFECTS: saves all data to JSON files
    private void save() {
        JsonWriterMovieDatabase jsonWriterMovieDatabase = new JsonWriterMovieDatabase(JSON_MOVIES);
        JsonWriterUserDataStorage jsonWriterUserDataStorage = new JsonWriterUserDataStorage(JSON_USERS);
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


    //todo from my movie list, see full description
}

