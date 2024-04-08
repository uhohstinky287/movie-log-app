package ui.tabs;

import model.EventLog;
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
    private JLabel recentMoviesTitle;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);
    private Font subheaderFont = new Font("Roboto", Font.BOLD, 20);
    private MovieLogAppGUI  controller;

    private JButton recentMovieButton1;
    private JButton recentMovieButton2;
    private JButton recentMovieButton3;

    private User user;

    private JPanel recentMoviesPanel;



    //constructor for home tabb
    public HomeTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;
        this.user = controller.getUser();
        placeGreeting();
        placeHomeButtons();
        addRefreshButton();
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
            getController().exitAPP(EventLog.getInstance());
        });
        this.add(b1);
        this.add(b2);
    }

    //EFFECTS: add recent movies
    private void placeRecentMovies() {
        recentMoviesTitle = new JLabel("RECENT MOVIES");
        recentMoviesTitle.setFont(subheaderFont);
        recentMoviesTitle.setBounds(230, 250, 200, 35);

        recentMoviesPanel = new JPanel();
        recentMoviesPanel.setBounds(50, 300, 500, 200);
        recentMoviesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 175, 30));

        Movie recentMovie1 = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 1));
        recentMovieButton1 = new JButton(getRecentMovieButtonLabel(recentMovie1));
        removeButtonBackground(recentMovieButton1);
        initializeRecentMovieButton(recentMovieButton1, recentMovie1);

        Movie recentMovie2 = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 2));
        recentMovieButton2 = new JButton(getRecentMovieButtonLabel(recentMovie2));
        removeButtonBackground(recentMovieButton2);
        initializeRecentMovieButton(recentMovieButton2, recentMovie2);

        Movie recentMovie3 = recMovie(user.getMyMovies().get(user.getTotalMoviesSeen() - 3));
        recentMovieButton3 = new JButton(getRecentMovieButtonLabel(recentMovie3));
        removeButtonBackground(recentMovieButton3);
        initializeRecentMovieButton(recentMovieButton3, recentMovie3);

        this.add(recentMoviesTitle);
        recentMoviesPanel.add(recentMovieButton1);
        recentMoviesPanel.add(recentMovieButton2);
        recentMoviesPanel.add(recentMovieButton3);
        this.add(recentMoviesPanel);
    }

    //EFFECTS: implements the logic for each recent Movie Button
    private void initializeRecentMovieButton(JButton recentMovie, Movie movie) {
        recentMovie.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, movie.movieDetailsWatched(controller.getUsername()));
        });
    }

    //EFFECTS: returns the label for the movie from the given movie
    private String getRecentMovieButtonLabel(Movie movie) {
        return movie.getMovieName() + " (" + movie.getMovieYear() + ")";
    }

    //EFFECTS: makes the button have no background
    private void removeButtonBackground(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    //EFFECT returns the movie from database from the given movie
    private Movie recMovie(Movie movie) {
        return controller.getDatabase().returnMovieFromDatabase(movie);
    }


    //EFFECTS: adds the refresh tab with its functionality
    private void addRefreshButton() {
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(230, 215, 140, 25);
        this.add(refreshButton);
        refreshButton.addActionListener(e -> {
            if (controller.getUser().getTotalMoviesSeen() >= 3) {
                if (recentMoviesPanel != null) {
                    this.remove(recentMoviesPanel);
                    this.remove(recentMoviesTitle);
                    this.revalidate();
                    this.repaint();
                }
                placeRecentMovies();
            }
        });

    }






}
