package ui.tabs;

import model.Movie;
import model.User;
import ui.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


// home tab (starting screen)
public class HomeTab extends Tab {
    private static final String INIT_GREETING = "Welcome to your movie log";
    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);
    private Font subheaderFont = new Font("Roboto", Font.BOLD, 20);
    private MovieLogAppGUI  controller;

    private JButton recentMovie1Button;
    private JButton recentMovie2;
    private JButton recentMovie3;
    private Border emptyBorder = BorderFactory.createEmptyBorder();
    private User user;

    private Color colour;


    //constructor for home tabb
    public HomeTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;
        this.user = controller.getUser();
        placeGreeting();
        placeHomeButtons();
        if (controller.getUser().getTotalMoviesSeen() >= 3) {
            placeRecentMovies();
        }
    }

    //EFFECTS: creates a greeting at the top of the home page
    private void placeGreeting() {
        String header = "Welcome to your Movie Log";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(125, 30, 400, 30);
        this.add(headerLabel);
    }


    //EFFECTS: creates a search for movie button
    private void placeHomeButtons() {
        JButton b1 = new JButton("Save All Changes");
        JButton b2 = new JButton("Exit without saving");
        b1.setBounds(150, 180, 140, 25);
        b2.setBounds(320, 180, 180, 25);


        b1.addActionListener(e -> controller.save());
        b2.addActionListener(e -> {
            headerLabel.setText("Goodbye!");
            getController().exitAPP();
        });
        this.add(b1);
        this.add(b2);
    }

    //EFFECTS: add recent movies
    private void placeRecentMovies() {
        JLabel recentMoviesTitle = new JLabel("RECENT MOVIES");
        recentMoviesTitle.setFont(subheaderFont);
        recentMoviesTitle.setBounds(50, 250, 200, 35);

        Movie recentMovie1Movie = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 1));
        recentMovie1Button = new JButton(getRecentMovieButtonLabel(recentMovie1Movie));
        recentMovie1Button.setBounds(50, 300, 200, 25);
        removeButtonBackground(recentMovie1Button);
        initializeRecentMovieButton(recentMovie1Button, recentMovie1Movie);

        Movie recentMovie2Movie = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 2));
        recentMovie2 = new JButton(getRecentMovieButtonLabel(recentMovie2Movie));
        recentMovie2.setBounds(255, 300, 200,25);
        removeButtonBackground(recentMovie2);
        initializeRecentMovieButton(recentMovie2, recentMovie2Movie);

        Movie recentMovie3Movie = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 3));
        recentMovie3 = new JButton(getRecentMovieButtonLabel(recentMovie3Movie));
        recentMovie3.setBounds(460, 300, 200, 25);
        removeButtonBackground(recentMovie3);
        initializeRecentMovieButton(recentMovie3, recentMovie3Movie);

        this.add(recentMoviesTitle);
        this.add(recentMovie1Button);
        this.add(recentMovie2);
        this.add(recentMovie3);
    }

    private void initializeRecentMovieButton(JButton recentMovie, Movie movie) {
        recentMovie.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, movie.movieDetailsWatched(controller.getUsername()));
        });
    }

    private String getRecentMovieButtonLabel(Movie movie) {
        return movie.getMovieName() + " (" + movie.getMovieYear() + ")";
    }

    private void removeButtonBackground(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    private Movie recMovie(Movie movie) {
        return controller.getDatabase().returnMovieFromDatabase(movie);
    }






}
