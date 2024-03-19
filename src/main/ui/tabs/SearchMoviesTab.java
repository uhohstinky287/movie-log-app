package ui.tabs;

import model.*;
import ui.MovieLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class SearchMoviesTab extends Tab {

    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    private JLabel headerLabel;
    private JLabel movieNameLabel;
    private JLabel releaseYearLabel;

    private JTextField movieNameField;
    private JTextField releaseYearField;
    private JTextField ratingField;
    private JTextField movieDirectorField;
    private JTextField descriptionField;

    private JButton searchButton;
    private JButton watchButton;
    private JButton newWrittenReviewButton;
    private JButton addReviewButton;
    private JButton addMovieToDatabaseButton;

    private JTextArea movieDetailsTextArea;
    private JTextArea reviewTextArea;

    private MovieLogAppGUI controller;

    private User user;
    private MovieDatabase database;

    private String movieName;
    private Integer movieYear;
    private Integer userRating;

    private Movie movieForSearch;

    private JPanel movieDetailsPanel; // Panel to display movie details
    private JPanel watchButtonPanel;
    private JPanel ratingPanel;
    private JPanel reviewPanel;
    private JPanel newWrittenReviewButtonPanel;
    private JPanel addMovieToDataBasePanel;


    // creates the functions and display for the SearchMovies Tab
    public SearchMoviesTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;


        placeHeader();
        placeLabelsAndFields();
        initializeSearchAction(searchButton);
    }


    // EFFECTS: places the header
    private void placeHeader() {
        String header = "Search Movies";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(100, 20, 300, 30);
        this.add(headerLabel);
    }

    //EFFECTS: adds the movie name and year labels and fields, as the search button
    private void placeLabelsAndFields() {
        movieNameLabel = new JLabel("Movie Name:");
        movieNameLabel.setBounds(50, 120, 100, 30);
        this.add(movieNameLabel);
        movieNameField = new JTextField();
        movieNameField.setBounds(160, 120, 150, 30);
        this.add(movieNameField);
        releaseYearLabel = new JLabel("Release Year:");
        releaseYearLabel.setBounds(50, 170, 100, 30);
        this.add(releaseYearLabel);
        releaseYearField = new JTextField(4);
        releaseYearField.setBounds(160, 170, 150, 30);
        this.add(releaseYearField);
        searchButton = new JButton("Search");
        searchButton.setBounds(160, 220, 100, 30);
        this.add(searchButton);
    }


    //EFFECTS: when search is pushed, movieName becomes the text in Movie field (1). Release year input is checked to
    // see if its valid, if not --> error (2) . A movie is created for search from the movie name and year. (3)
    // calls removeAllAdditionalPanels() to remove all the additional panels (4)
    // First it checks to see if the movie is in user's movie list, if so then performs displayMovieWatched() (5)
    // If not, then checks to see if the movie is in the database, if so then performs displayMovieUnWatched() (6)
    // If not, then provides the option to add the movie to the database (7) //todo

    private void initializeSearchAction(JButton searchButton) {
        searchButton.addActionListener(e -> {
            String movieName = movieNameField.getText().toUpperCase(); //(1)
            String yearInput = releaseYearField.getText();
            if (isValidYear(yearInput)) {
                movieYear = Integer.parseInt(yearInput);
            } else { //(2)
                JOptionPane.showMessageDialog(this, "Please enter a valid release year.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
            Movie movie = new Movie(movieName, movieYear); //(3)
            removeAllAdditionPanels(); //(4)
            if (controller.getUser().isMovieInMyMovieList(movie)) {
                displayMovieWatched(movie); //(5)
            } else {
                if (controller.getDatabase().isMovieInDatabase(movie)) {
                    displayMovieUnWatched(movie); //(6)
                } else {
                    askAddToDatabase(movie);
                    //(7)
                }
            }
        });
    }

    //EFFECTS: JOptionPane pops up to ask if user wants to add to database, if so then
    // initializeAddMovieToDatabasePanel(movie)
    private void askAddToDatabase(Movie movie) {
        Object[] options = {"Yes", "No"};
        int selectedOption = JOptionPane.showOptionDialog(this,
                "This movie is not in our database\n "
                        + "Would you like to add it?", "Add Movie?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_OPTION) {
            initializeAddMovieToDatabasePanel(movie);
        } else {
            JOptionPane.getRootFrame().dispose();
        }
    }

    //EFFECTS: creates addMovieToDataBasePanel
    private void initializeAddMovieToDatabasePanel(Movie movie) {
        addMovieToDataBasePanel = new JPanel();
        addMovieToDataBasePanel.setBounds(380, 60, 210, 130);
        addMovieToDataBasePanel.setLayout(new BoxLayout(addMovieToDataBasePanel, BoxLayout.Y_AXIS));

        JLabel whoisDirector = new JLabel("Director: ");
        movieDirectorField = new JTextField();
        JLabel writeDescription = new JLabel("Write a short objective description");
        descriptionField = new JTextField();
        addMovieToDatabaseButton = new JButton("Add Movie to database");
        initializeAddMovieToDatabaseButton(addMovieToDatabaseButton, movie);
        addMovieToDataBasePanel.add(whoisDirector);
        addMovieToDataBasePanel.add(descriptionField);
        addMovieToDataBasePanel.add(writeDescription);
        addMovieToDataBasePanel.add(movieDirectorField);
        addMovieToDataBasePanel.add(addMovieToDatabaseButton);
        this.add(addMovieToDataBasePanel);
        addMovieToDataBasePanel.setVisible(true);
        this.revalidate();
    }

    //EFFECTS: implements logic for addMovieToDatabaseButton
    private void initializeAddMovieToDatabaseButton(JButton addMovieToDatabaseButton, Movie movie) {
        addMovieToDatabaseButton.addActionListener(e -> {
            if (movieDirectorField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "You cant leave any blank!");
            } else {
                movie.setDirector(movieDirectorField.getText());
                movie.setMovieDescription(descriptionField.getText());
                controller.getDatabase().addMovieToDatabase(movie);
                JOptionPane.showMessageDialog(this, "movie added to database");
                removeAllAdditionPanels();
            }
        });
    }



    // EFFECTS: Calls on initializeMovieDetailsPanel() and initializeWatchButtonPanel() to create the panels (1)
    // Then it looks in the database for the movie, returns it and assigns it to dataBaseMovie (2)
    // Then it calls movieDetailsUnWatched() from the databaseMovie and assign that to detailsUnwatched String (3)
    // Then it adds detailsUnwatched to movieDetailsTextArea (this was created in initializeMovieDetailsPanel()) (4)
    // Then it implements the logic of watchButton (5)
    // Then it revalidates SearchMoviesTab (6)
    private void displayMovieUnWatched(Movie movie) {
        initializeMovieDetailsPanel();
        initializeWatchButtonPanel(); //(1)
        Movie databaseMovie = controller.getDatabase().returnMovieFromDatabase(movie); //(2)
        String detailsUnwatched = databaseMovie.movieDetailsUnWatched(); //(3)
        movieDetailsTextArea.setText(detailsUnwatched); //(4)
        initializeAddMovieFromDatabase(watchButton, databaseMovie); //(5)
        this.revalidate(); //(6)
    }

    // EFFECTS: makes the movie details panel with non-editable text area
    // creates movieDetailsPanel and places it at 50, 270 of size 300, 200
    // adds movieDetailsPanel to searchMoviesTab
    // creates movieDetailsTextArea which is non-editable area
    // creates a JScrollPane with movieDetailsTextArea adds it to movieDetailsPanel centered
    private void initializeMovieDetailsPanel() {
        movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new GridLayout(0, 1));
        movieDetailsPanel.setBounds(50, 270, 300, 200);

        this.add(movieDetailsPanel);

        movieDetailsTextArea = new JTextArea();
        movieDetailsTextArea.setEditable(false);
        movieDetailsPanel.add(new JScrollPane(movieDetailsTextArea), BorderLayout.CENTER);
    }

    //EFFECTS: creates a watchButtonPanel right under the movieDetailsPanel with the watchButton
    private void initializeWatchButtonPanel() {
        watchButtonPanel = new JPanel();
        watchButtonPanel.setLayout(new GridLayout(0, 1));
        watchButtonPanel.setBounds(50, 500, 300, 50);
        watchButton = new JButton("Seen this movie?");
        watchButton.setBounds(70,520, 150, 20);
        watchButtonPanel.add(watchButton);
        this.add(watchButtonPanel);
    }

    //EFFECTS: implements the logic for watchButton which adds a movie from Database
    // creates a panel with at 380, 270 of size 210, 85 (1)
    // creates rating label and fields (2)
    // creates JButton addReviewButton (3)
    // creates JButton addWithoutReviewButton (4)
    // calls on initialize (each button) to implement the logic of each (5)
    // adds all 4 items to ratingPanel (6)
    // adds ratingPanel to Search Movies tab (7)
    // revalidates and repaints (8)
    private void initializeAddMovieFromDatabase(JButton watchButton, Movie movie) {
        watchButton.addActionListener(e -> {
            ratingPanel = new JPanel();
            ratingPanel.setBounds(380, 270, 210, 85);
            ratingPanel.setVisible(true);
            ratingPanel.setLayout(null); //(1)
            JLabel ratingLabel = new JLabel("Rate Movie /100");
            ratingLabel.setBounds(0, 0, 100, 20);
            ratingField = new JTextField(10);
            ratingField.setBounds(100, 0, 100, 20);  //(2)
            addReviewButton = new JButton("Write a review");
            addReviewButton.setBounds(0, 30, 200, 20); //(3)
            JButton addWithoutReviewButton = new JButton("Add without review");
            addWithoutReviewButton.setBounds(0, 55, 200, 20);     //(4)
            initializeAddReviewButton(addReviewButton, movie);
            initializeAddWithoutReviewButton(addWithoutReviewButton, movie); //(5)
            ratingPanel.add(ratingLabel);
            ratingPanel.add(ratingField);
            ratingPanel.add(addReviewButton);
            ratingPanel.add(addWithoutReviewButton); //(6)
            this.add(ratingPanel); //(7)
            ratingPanel.revalidate();
            this.revalidate();
            this.repaint(); }); //(8)
    }


    //EFFECTS: implements the logic for addWithoutReviewButton: adds the movie to user's movie and review to database
    // calls isValidRating to see if userRating is a valid rating, if not it displays error (1)
    // if valid, then it creates a review called userReview with the username and rating (2)
    // adds the review to database (3)
    // adds the movie from the database to the user's MyMovies  (4)
    // displays dialogue saying the movie has been added to list (5)
    // calls removeAllAdditionalPanels() to remove all panels and clear SearchMoviesTab (6)
    private void initializeAddWithoutReviewButton(JButton addWithoutReviewButton, Movie movie) {
        addWithoutReviewButton.addActionListener(e -> {
            String ratingInput = ratingField.getText();
            if (isValidRating(ratingInput)) {
                userRating = Integer.parseInt(ratingInput);
                Review userReview = new Review(controller.getUsername(), userRating); //(2)
                controller.getDatabase().addToAverageRating(movie, userReview); // (3)
                controller.getUser().addMovie(controller.getDatabase().returnMovieFromDatabase(movie)); //(4)
                JOptionPane.showMessageDialog(this, movie.getMovieName()
                        + " has been added to your list"); //(5)
                removeAllAdditionPanels(); //(6)
            } else {
                JOptionPane.showMessageDialog(this, "Enter a valid rating 0-100"); //(1)
            }
        });
    }

    //EFFECTS: implements the logic for addReviewButton: creates a review panel where user can type review
    // calls isValidRating to see if userRating is a valid rating, if not it displays error (1)
    // if valid, then removes addReviewButton from ratingPanel (2)
    // sets userRating as ratingInput (3)
    // calls initializeReviewPanel() and initializeNewWrittenReviewButtonPanel() to initialize the panels (4)
    // calls initializeNewWrittenReviewButton() to implement the logic for newWrittenReviewButton (5)
    private void initializeAddReviewButton(JButton addReviewButton, Movie movie) {
        addReviewButton.addActionListener(e -> {
            String ratingInput = ratingField.getText();
            if (isValidRating(ratingInput)) { //(1)
                ratingPanel.remove(addReviewButton); //(2)
                userRating = Integer.parseInt(ratingInput); //(3)
                initializeReviewPanel();
                initializeNewWrittenReviewButtonPanel(); //(4)
                initializeNewWrittenReviewButton(newWrittenReviewButton, movie); //(5)
            } else {
                JOptionPane.showMessageDialog(this, "Enter a valid rating 0-100"); //(1)
            }
        });
    }

    //EFFECTS: creates a reviewPanel with a reviewTextArea
    private void initializeReviewPanel() {
        reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setBounds(380, 380, 200, 120);
        reviewTextArea = new JTextArea();
        reviewTextArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);
        reviewPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(reviewPanel);
        reviewPanel.setVisible(true);
        this.revalidate();
    }

    //EFFECTS: creates a newWrittenReviewButton panel with the newWrittenReviewButton
    private void initializeNewWrittenReviewButtonPanel() {
        newWrittenReviewButtonPanel = new JPanel();
        newWrittenReviewButtonPanel.setLayout(new GridLayout(0,1));
        newWrittenReviewButtonPanel.setBounds(380, 500, 200, 50);
        newWrittenReviewButton = new JButton("Add movie with review");
        newWrittenReviewButton.setBounds(380,500, 200, 20);
        newWrittenReviewButtonPanel.add(newWrittenReviewButton);
        this.add(newWrittenReviewButtonPanel);

    }

    //EFFECTS: implements the logic for newWrittenReviewButton: adds movie with written review to database and MyMovies
    private void initializeNewWrittenReviewButton(JButton newWrittenReviewButton, Movie movie) {
        newWrittenReviewButton.addActionListener(e -> {
            String writtenReview = reviewTextArea.getText();
            Review movieReview = new Review(controller.getUsername(), userRating);
            movieReview.setWrittenReview(writtenReview);
            controller.getDatabase().addToAverageRating(movie, movieReview);
            controller.getUser().addMovie(controller.getDatabase().returnMovieFromDatabase(movie));
            JOptionPane.showMessageDialog(this, "movie + review added");
            removeAllAdditionPanels();
        });

    }

    //EFFECTS: displays the details of a watched movie in the movieDetailsTextArea
    private void displayMovieWatched(Movie movie) {
        removeAllAdditionPanels();
        initializeMovieDetailsPanel();
        String detailsWatched =
                controller.getUser().isMovieInMyListReturnMovie(movie).movieDetailsWatched(controller.getUsername());
        movieDetailsTextArea.setText(detailsWatched);
        movieDetailsPanel.revalidate();
        movieDetailsPanel.repaint();
    }

    //EFFECTS: checks to see if a movie's year is valid from (1800-current year)
    private boolean isValidYear(String year) {
        try {
            int yearValue = Integer.parseInt(year);
            return yearValue >= 1800 && yearValue <= java.time.LocalDate.now().getYear();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //EFFECTS: checks to see if a rating is between 0-100 and an integer
    private boolean isValidRating(String rating) {
        try {
            int ratingValue = Integer.parseInt(rating);
            return ratingValue <= 100 && ratingValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: removes all the additional panels
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void removeAllAdditionPanels() {
        if (movieDetailsPanel != null) {
            this.remove(movieDetailsPanel);
            movieDetailsPanel = null;
        }
        if (watchButtonPanel != null) {
            this.remove(watchButtonPanel);
            watchButtonPanel = null;
        }
        if (ratingPanel != null) {
            this.remove(ratingPanel);
            ratingPanel = null;
        }
        if (reviewPanel != null) {
            this.remove(reviewPanel);
            reviewPanel = null;
        }
        if (newWrittenReviewButtonPanel != null) {
            this.remove(newWrittenReviewButtonPanel);
            newWrittenReviewButtonPanel = null;
        }
        if (addMovieToDataBasePanel != null) {
            this.remove(addMovieToDataBasePanel);
            addMovieToDataBasePanel = null;
        }
        this.revalidate();
        this.repaint();
    }
}
