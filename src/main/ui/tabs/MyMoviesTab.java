package ui.tabs;

import ui.*;

import javax.swing.*;
import java.awt.*;

// my movies tab
public class MyMoviesTab extends Tab {

    private MovieLogAppGUI controller;

    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    private JPanel myMoviesListPanel;
    private JTextArea myMoviesList;

    private String myMoviesListString;

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

    //EFFECTS: creates a MyMoviesListPanel
    private void placeMyMovies() {
        myMoviesListPanel = new JPanel();
        myMoviesListPanel.setLayout(new GridLayout(0,1));
        myMoviesListPanel.setBounds(40, 100, 600, 400);
        myMoviesList = new JTextArea();
        myMoviesList.setEditable(false);
        myMoviesListPanel.add(new JScrollPane(myMoviesList), BorderLayout.CENTER);
        if (controller.getUser().getTotalMoviesSeen() == 0) {
            myMoviesListString = "You have not rated any movies yet";
        } else {
            myMoviesListString = controller.getUsername() + "'s Movies\t"
                    + controller.getUser().viewMoviesNotEmpty(controller.getUsername());
        }
        myMoviesList.setText(myMoviesListString);
        this.add(myMoviesListPanel);
        this.revalidate();
        this.repaint();
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
            if (controller.getUser().getTotalMoviesSeen() == 0) {
                myMoviesListString = "You have not rated any movies yet";
            } else {
                myMoviesListString = controller.getUsername() + "'s Movies\t"
                        + controller.getUser().viewMoviesNotEmpty(controller.getUsername());
            }
            myMoviesList.setText(myMoviesListString);
        });
    }

}
