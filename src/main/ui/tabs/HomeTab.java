package ui.tabs;

import ui.*;

import javax.swing.*;
import java.awt.*;


// home tab (starting screen)
public class HomeTab extends Tab {
    private static final String INIT_GREETING = "Welcome to your movie log";
    private JLabel greeting;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);
    private MovieLogAppGUI  controller;


    //constructor for home tabb
    public HomeTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));
        this.controller = controller;

        placeGreeting();
        placeHomeButtons();
    }

    //EFFECTS: creates a greeting at the top of the home page
    private void placeGreeting() {
        greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 3);
        greeting.setFont(headerFont);
        this.add(greeting);
    }


    //EFFECTS: creates a search for movie button
    private void placeHomeButtons() {
        JButton b1 = new JButton("Save All Changes");
        JButton b2 = new JButton("Exit without saving");

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(e -> controller.save());
        b2.addActionListener(e -> {
            greeting.setText("Goodbye!");
            getController().exitAPP();
        });

        this.add(buttonRow);

    }






}
