package ui.tabs;

import model.Movie;
import ui.*;

import javax.swing.*;
import java.awt.*;

// my movies tab
public class MyMoviesTab extends Tab {

    private MovieLogAppGUI controller;

    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    private JPanel myMoviesListPanel;

    private JButton refreshButton;

    // constructor
    public MyMoviesTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;

        placeHeader();
        placeMyMovies();
        placeRefreshButton();
    }

    //EFFECTS: creates a greeting at the top of the home page
    private void placeHeader() {
        String header = "Your Movies";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setSize(WIDTH, HEIGHT / 3);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(175, 30, 300, 30);
        this.add(headerLabel);
    }

    //EFFECTS: places my movies with a flowlayout
    private void placeMyMovies() {
        myMoviesListPanel = new JPanel();
        myMoviesListPanel.setBounds(40, 100, 600, 400);
        myMoviesListPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        if (controller.getUser().getTotalMoviesSeen() == 0) {
            placeEmptyMovies();
        } else {
            for (Movie m : controller.getUser().getMyMovies()) {
                JButton movieButton = new JButton(m.getMovieName() + " (" + m.getMovieYear() + ")");
                movieButton.setOpaque(false);
                movieButton.setContentAreaFilled(false);
                movieButton.setBorderPainted(false);
                movieButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, m.movieDetailsWatched(controller.getUsername()));
                });
                myMoviesListPanel.add(movieButton);
            }
        }
        this.add(myMoviesListPanel);
        this.revalidate();
        this.repaint();
    }

    private void placeEmptyMovies() {
        JLabel noMoviesLabel = new JLabel("You have not rated any movies yet");
        myMoviesListPanel.add(noMoviesLabel);
    }

    //EFFECTS: places the refresh Button
    private void placeRefreshButton() {
        refreshButton = new JButton("Refresh");
        refreshButton.setSize(WIDTH, 20);
        refreshButton.setBounds(500, 30, 100,  25);
        initializeRefreshButton(refreshButton);
        this.add(refreshButton);
        this.revalidate();
        this.repaint();
    }

    //EFFECTS: implements the logic for the refresh button
    private void initializeRefreshButton(JButton refreshButton) {
        refreshButton.addActionListener(e -> {
            this.remove(myMoviesListPanel);
            this.revalidate();
            this.repaint();
            placeMyMovies();
        });
    }

}
