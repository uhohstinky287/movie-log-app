package ui.tabs;

import model.Movie;
import model.MovieDatabase;
import model.User;
import model.UserDataStorage;
import ui.MovieLogAppGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SearchMoviesTab extends Tab {

    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    private JLabel headerLabel;
    private JLabel movieNameLabel;
    private JLabel releaseYearLabel;
    private JTextField movieNameField;
    private JTextField releaseYearField;
    private JButton searchButton;

    private JTextArea movieDetailsTextArea;

    private MovieLogAppGUI controller;

    private User user;
    private MovieDatabase database;

    private String movieName;
    private Integer movieYear;

    private Movie movieForSearch;

    private JPanel movieDetailsPanel; // Panel to display movie details

    private Map<Movie, JLabel> movieDetailsMap; // Map to keep track of movie details labels

    public SearchMoviesTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;


        placeHeader();
        placeLabelsAndFields();
        initializeSearchAction(searchButton);
        initializeMovieDetailsPanel();
    }


    private void placeHeader() {
        String header = "Search Movies";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(100, 20, 300, 30);
        this.add(headerLabel);
    }

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
        releaseYearField = new JTextField();
        releaseYearField.setBounds(160, 170, 150, 30);
        this.add(releaseYearField);
        searchButton = new JButton("Search");
        searchButton.setBounds(160, 220, 100, 30);
        this.add(searchButton);
    }


    private void initializeSearchAction(JButton searchButton) {
        searchButton.addActionListener(e -> {
            String movieName = movieNameField.getText().toUpperCase();
            String yearInput = releaseYearField.getText();
            if (isValidYear(yearInput)) {
                movieYear = Integer.parseInt(yearInput);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid release year.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
            Movie movie = new Movie(movieName, movieYear);

            if (controller.getUser().isMovieInMyMovieList(movie)) {
                JOptionPane.showMessageDialog(this, "Movie is in your list");
//                checkMyMovies(movie);
            } else {
                if (controller.getDatabase().isMovieInDatabase(movie)) {
//                    JOptionPane.showMessageDialog(this, "Movie is in database");
                    checkMyMovies(movie);
                } else {
                    JOptionPane.showMessageDialog(this, "Movie is not in the database");
                }
            }
        });
    }

    private void initializeMovieDetailsPanel() {
        movieDetailsPanel = new JPanel();
        movieDetailsPanel.setLayout(new GridLayout(0, 1));
        movieDetailsPanel.setBounds(50, 270, 300, 150); // Adjust the bounds as needed
        this.add(movieDetailsPanel);

        movieDetailsTextArea = new JTextArea();
        movieDetailsTextArea.setEditable(false); // Make it read-only
        movieDetailsPanel.add(new JScrollPane(movieDetailsTextArea), BorderLayout.CENTER);
    }


    private void checkMyMovies(Movie movie) {
        movieDetailsPanel.removeAll();

        // Get movie details
        String detailsWatched =
                controller.getDatabase().returnMovieFromDatabase(movie).movieDetailsWatched(controller.getUsername());
        String detailsUnwatched =
                controller.getDatabase().provideDetailsUnwatched(movie);
        movieDetailsTextArea.setText(detailsUnwatched);

        // Display movie details
        JLabel movieDetailsLabelWatched = new JLabel(detailsWatched);
        movieDetailsPanel.add(movieDetailsLabelWatched);

        // Update UI
        movieDetailsPanel.revalidate();
        movieDetailsPanel.repaint();
    }

    private boolean isValidYear(String year) {
        try {
            int yearValue = Integer.parseInt(year);
            return yearValue >= 1800 && yearValue <= java.time.LocalDate.now().getYear();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
