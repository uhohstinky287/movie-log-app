package ui.tabs;

import ui.MovieLogAppGUI;

import javax.swing.*;
import java.awt.*;

// handles the controls for the View Friends Tab
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

    // constructor
    public ViewFriendsTab(MovieLogAppGUI controller) {
        super(controller);
        setLayout(null);
        this.controller = controller;

        placeHeader();
        placeFriendsList();
        placeSearchUsers();
    }

    //EFFECTS: places the Your FRIENDS header
    private void placeHeader() {
        String header = "Your Friends";
        headerLabel = new JLabel(header, JLabel.CENTER);
        headerLabel.setFont(headerFont);
        headerLabel.setBounds(175, 30, 300, 30);
        this.add(headerLabel);
    }

    //EFFECTS: places the panels and labels and text areas of friends
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

    //EFFECTS: fills the friendsList Text Area
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

    //EFFECTS: implements the logic for the refresh button
    private void initializeRefreshFriendsButton(JButton refreshFriendsButton) {
        refreshFriendsButton.addActionListener(e -> {
            initializeFriendsList();
        });
    }

    //EFFECTS: places the Search users label and fiels
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

    //EFFECTS: implements the logic for the searchUsersButton
    private void initializeSearchUsersButton(JButton searchUsersButton) {
        searchUsersButton.addActionListener(e -> {
            removeExtraPanels();
            String otherUsername = searchUsersField.getText();
            if (!controller.getAllUsers().isUserInDataBase(otherUsername)) {
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

    //EFFECTS: fills the otherUsersMovies Text Area
    private void fillMovies() {
        String otherUsername = searchUsersField.getText();
        if (controller.getAllUsers().returnUserFromDatabase(otherUsername).getTotalMoviesSeen() == 0) {
            otherUsersMoviesString = otherUsername + "has not rated any movies";
        } else {
            otherUsersMoviesString = otherUsername + "'s Movies: \n"
                    + controller.getAllUsers().returnUserFromDatabase(otherUsername).viewMoviesNotEmpty(otherUsername);
        }
    }

    //EFFECTS: places the addFriend Button Panel and Button
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

    //EFFECTS: implements the logic for the add friends button
    private void initializeAddFriendButton(JButton addFriendButton) {
        addFriendButton.addActionListener(e -> {
            controller.getUser().addFriend(searchUsersField.getText());
            JOptionPane.showMessageDialog(this, searchUsersField.getText()
                    + "has been friended");
            removeExtraPanels();
        });
    }

    //EFFECTS: removes all panels that aren't what the user started with
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
