package ui.tabs;

import ui.MovieLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class ViewFriendsTab extends Tab {

    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    public ViewFriendsTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(new GridLayout(3, 1));

        placeHeader();


    }

    private void placeHeader() {
        String header = "Your Friends";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setSize(WIDTH, HEIGHT / 3);
        headerLabel.setFont(headerFont);
        this.add(headerLabel);
    }

}
