package ui.tabs;

import ui.MovieLogAppGUI;

import javax.swing.*;
import java.awt.*;

public class ViewFriendsTab extends Tab {

    private MovieLogAppGUI controller;

    private JLabel headerLabel;
    private Font headerFont = new Font("Roboto", Font.BOLD, 25);

    private JTextArea friendsList;
    private String friendsListString;
    private String otherUsersMoviesString;

    private JPanel friendsListPanel;
    private JPanel otherUsersMoviesPanel;
    private JPanel addFriendButtonPanel;

    private JButton refreshFriendsButton;
    private JButton searchUsersButton;
    private JButton addFriendButton;

    private JTextField searchUsersField;

    public ViewFriendsTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;

        placeHeader();
        placeFriendsList();
        placeSearchUsers();
    }

    private void placeHeader() {
        String header = "Your Friends";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(175, 30, 300, 30);
        this.add(headerLabel);
    }

    private void placeFriendsList() {
        JLabel yourFriendsLabel = new JLabel("Your Friends:");
        yourFriendsLabel.setBounds(40, 100, 225, 20);
        this.add(yourFriendsLabel);

        refreshFriendsButton = new JButton("Refresh List");
        refreshFriendsButton.setBounds(40, 440, 225, 20);
        this.add(refreshFriendsButton);
        initializeRefreshFriendsButton(refreshFriendsButton);

        friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new GridLayout(0,1));
        friendsListPanel.setBounds(40, 130, 225, 300);
        friendsList = new JTextArea();
        friendsList.setEditable(false);
        friendsListPanel.add(new JScrollPane(friendsList), BorderLayout.CENTER);
        initializeFriendsList();
        this.add(friendsListPanel);
        this.revalidate();
        this.repaint();
    }

    private void initializeFriendsList() {
        if (controller.getUser().getFriends().isEmpty()) {
            friendsListString = "\n You have no friends :(";
        } else {
            friendsListString = "\t" + controller.getUser().viewFriendsNotEmpty();
        }
        friendsList.setText(friendsListString);
        friendsListPanel.revalidate();
        friendsListPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    private void initializeRefreshFriendsButton(JButton refreshFriendsButton) {
        refreshFriendsButton.addActionListener(e -> {
            initializeFriendsList();
        });
    }

    private void placeSearchUsers() {
        JLabel searchUsersLabel = new JLabel("Search User:");
        searchUsersLabel.setBounds(400, 100, 150, 20);
        this.add(searchUsersLabel);

        searchUsersField = new JTextField();
        searchUsersField.setBounds(475, 100, 150, 20);
        this.add(searchUsersField);

        searchUsersButton = new JButton("Search");
        searchUsersButton.setBounds(500, 130, 100, 20);
        this.add(searchUsersButton);
        initializeSearchUsersButton(searchUsersButton);

        this.revalidate();
    }

    private void initializeSearchUsersButton(JButton searchUsersButton) {
        searchUsersButton.addActionListener(e -> {
            removeExtraPanels();
            String otherUsername = searchUsersField.getText();
            if (!controller.getAllUsers().isUserInDataBaseLOL(otherUsername)) {
                JOptionPane.showMessageDialog(this, "User not in database");
            } else {
                otherUsersMoviesPanel = new JPanel();
                otherUsersMoviesPanel.setLayout(new GridLayout(0, 1));
                otherUsersMoviesPanel.setBounds(400, 160, 225, 300);
                this.add(otherUsersMoviesPanel);
                JTextArea otherUsersMovies = new JTextArea();
                otherUsersMovies.setEditable(false);
                fillMovies();
                otherUsersMovies.setText(otherUsersMoviesString);
                otherUsersMoviesPanel.add(otherUsersMovies);
                if (!controller.getUser().getFriends().contains(searchUsersField.getText())) {
                    placeAddFriendButtonPanel();
                }
                this.revalidate();
                this.repaint();
            }
        });
    }

    private void fillMovies() {
        String otherUsername = searchUsersField.getText();
        if (controller.getAllUsers().returnUserFromDatabase(otherUsername).getTotalMoviesSeen() == 0) {
            otherUsersMoviesString = otherUsername + "has not rated any movies";
        } else {
            otherUsersMoviesString = otherUsername + "'s Movies: \n"
                    + controller.getAllUsers().returnUserFromDatabase(otherUsername).viewMoviesNotEmpty(otherUsername);
        }
    }

    private void placeAddFriendButtonPanel() {
        addFriendButtonPanel = new JPanel();
        addFriendButtonPanel.setLayout(new GridLayout(0, 1));
        addFriendButtonPanel.setBounds(400, 470, 225, 25);

        addFriendButton = new JButton("Add Friend");
        addFriendButtonPanel.add(addFriendButton);
        initializeAddFriendButton(addFriendButton);

        this.add(addFriendButtonPanel);
        this.validate();
        this.repaint();
    }

    private void initializeAddFriendButton(JButton addFriendButton) {
        addFriendButton.addActionListener(e -> {
            controller.getUser().addFriend(searchUsersField.getText());
            JOptionPane.showMessageDialog(this, searchUsersField.getText()
                    + "has been friended");
            removeExtraPanels();
        });
    }

    private void removeExtraPanels() {
        if (otherUsersMoviesPanel != null) {
            this.remove(otherUsersMoviesPanel);
            otherUsersMoviesPanel = null;
        }
        if (addFriendButtonPanel != null) {
            this.remove(addFriendButtonPanel);
            addFriendButtonPanel = null;
        }
        this.revalidate();
        this.repaint();
    }

}
