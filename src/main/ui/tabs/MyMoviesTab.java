package ui.tabs;

import ui.*;

import javax.swing.*;
import java.awt.*;

public class MyMoviesTab extends Tab {

    private MovieLogAppGUI controller;

    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    public MyMoviesTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));
        this.controller = controller;

        placeHeader();
    }

    //EFFECTS: creates a greeting at the top of the home page
    private void placeHeader() {
        String header = "Your Movies";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setSize(WIDTH, HEIGHT / 3);
        headerLabel.setFont(headerFont);
        this.add(headerLabel);
    }

}
