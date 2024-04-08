package ui.tabs;

import ui.MovieLogApp;
import ui.*;

import javax.swing.*;
import java.awt.*;

//abstract class for all the Tabs
public abstract class Tab extends JPanel {

    private final MovieLogAppGUI movieLogAppGui;

    //REQUIRES: MovieLogApp controller that holds this tab
    public Tab(MovieLogAppGUI controller) {
        this.movieLogAppGui = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the MovieLogApp controller for this tab
    public MovieLogAppGUI getController() {
        return movieLogAppGui;
    }

}
