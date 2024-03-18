package ui;


import model.MovieDatabase;
import model.User;
import model.UserDataStorage;
import persistence.JsonReaderMovieDatabase;
import persistence.JsonReaderUserDataStorage;
import persistence.JsonWriterMovieDatabase;
import persistence.JsonWriterUserDataStorage;
import ui.tabs.HomeTab;
import ui.tabs.MyMoviesTab;
import ui.tabs.SearchMoviesTab;
import ui.tabs.ViewFriendsTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MovieLogAppGUI {

    private User user;
    private MovieDatabase database;
    private UserDataStorage allUsers;

    private static final String JSON_MOVIES = "./data/movieDatabase.json";
    private static final String JSON_USERS = "./data/userDataStorage.json";

    private String username;
    private String password;

    private JFrame startingMenuFrame;
    private JFrame userHomeFrame;


    private JPanel startingMenuPanel;


    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton confirmCreateAccountButton;
    private JButton returnToStartingScreenButton;
    private JPasswordField passwordConfirmationField;

    private JPanel createAccountPanel;
    private JPanel homeTab;
    private JPanel myMoviesTab;
    private JPanel searchMoviesTab;
    private JPanel viewFriendsTab;


    private JTabbedPane sidebar;
    private static int SCREEN_WIDTH = 1368;
    private static int SCREEN_HEIGHT = 912;
    private static int FRAME_WIDTH = SCREEN_WIDTH - 10;
    private static int FRAME_HEIGHT = SCREEN_HEIGHT - 50;

    // runs the Movie Log App GUI
    public MovieLogAppGUI() throws FileNotFoundException {
        initializeApp();
        startingScreen();
    }

    //EFFECTS: initializes the application by loading the movie database/users and initili
    private void initializeApp() {
        loadDatabase();
        loadUsers();
    }

    // MODIFIES: this
    // EFFECTS: loads database from file
    private void loadDatabase() {
        database = new MovieDatabase();
        JsonReaderMovieDatabase jsonReaderMovieDatabase = new JsonReaderMovieDatabase(JSON_MOVIES);
        try {
            database = jsonReaderMovieDatabase.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_MOVIES);
        }
    }

    //EFFECTS: loads the list of all users from Json
    private void loadUsers() {
        allUsers = new UserDataStorage();
        JsonReaderUserDataStorage jsonReaderUserDataStorage = new JsonReaderUserDataStorage(JSON_USERS);
        try {
            allUsers = jsonReaderUserDataStorage.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_USERS);
        }
    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void startingScreen() {
        startingMenuFrame = new JFrame("Start App");
        startingMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startingMenuFrame.setSize(450, 250);
        startingMenuFrame.setLocationRelativeTo(null);
        startingMenuPanel = new JPanel();
        startingMenuPanel.setLayout(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:", JLabel.CENTER);
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:", JLabel.CENTER);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        startingMenuPanel.add(usernameLabel);
        startingMenuPanel.add(usernameField);
        startingMenuPanel.add(passwordLabel);
        startingMenuPanel.add(passwordField);
        startingMenuPanel.add(loginButton);
        startingMenuPanel.add(createAccountButton);
        startingMenuFrame.add(startingMenuPanel);
        startingMenuFrame.setVisible(true);
        loginButton.addActionListener(e ->  {
            username = usernameField.getText().toLowerCase();
            password = new String(passwordField.getPassword());
            loginMenu(username, password); });
        createAccountButton.addActionListener(e ->  {
            createAccountPanel(); });
    }

    private void loginMenu(String username, String password) {
        if (allUsers.isUserInDataBaseLOL(username)) {
            if (allUsers.returnUserFromDatabase(username).getPassword().equals(password)) {
                user = allUsers.returnUserFromDatabase(username);
                homeScreen();
            } else {
                JOptionPane.showMessageDialog(startingMenuFrame, "Incorrect Password");
            }
        } else {
            JOptionPane.showMessageDialog(startingMenuFrame, "Username not in database");
        }
    }

    private void createAccountPanel() {
        startingMenuFrame.remove(startingMenuPanel);
        createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new GridLayout(5, 2));
        addCreateAccountFields();
        confirmCreateAccountButtonActions(confirmCreateAccountButton);
        returnToStartingScreenButtonActions(returnToStartingScreenButton);
    }

    private void addCreateAccountFields() {
        JLabel usernameLabel = new JLabel("Create a username:", JLabel.CENTER);
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Create a password:", JLabel.CENTER);
        passwordField = new JPasswordField();
        JLabel passwordRepeatLabel = new JLabel("Confirm password:", JLabel.CENTER);
        passwordConfirmationField = new JPasswordField();
        confirmCreateAccountButton = new JButton("Create Account");
        returnToStartingScreenButton = new JButton("Return to Start");

        createAccountPanel.add(usernameLabel);
        createAccountPanel.add(usernameField);
        createAccountPanel.add(passwordLabel);
        createAccountPanel.add(passwordField);
        createAccountPanel.add(passwordRepeatLabel);
        createAccountPanel.add(passwordConfirmationField);
        createAccountPanel.add(returnToStartingScreenButton);
        createAccountPanel.add(confirmCreateAccountButton);


        startingMenuFrame.add(createAccountPanel);
        startingMenuFrame.revalidate();
    }

    private void confirmCreateAccountButtonActions(JButton confirmCreateAccountButton) {
        confirmCreateAccountButton.addActionListener(e -> {
            username =  usernameField.getText().toLowerCase();
            password = new String(passwordField.getPassword());
            String confirmPassword = new String(passwordConfirmationField.getPassword());

            if (allUsers.isUserInDataBaseLOL(username)) {
                JOptionPane.showMessageDialog(startingMenuFrame, "Username is taken");
            } else {
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(startingMenuFrame, "Passwords do not match");
                } else {
                    user = new User(username);
                    user.setPassword(password);
                    JOptionPane.showMessageDialog(startingMenuFrame, "Account created successfully!");
                    allUsers.addUserToDatabaseLOL(user);
                    homeScreen();
                }
            }
        });
    }

    private void returnToStartingScreenButtonActions(JButton returnToStartingScreenButton) {
        returnToStartingScreenButton.addActionListener(e -> {
            startingMenuFrame.dispose();
            startingScreen();

        });
    }


    private void homeScreen() {
        startingMenuFrame.setVisible(false);
        startingMenuFrame.dispose();

        userHomeFrame = new JFrame("Movie Log App");
        userHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userHomeFrame.setSize(800, 600);
        userHomeFrame.setLocationRelativeTo(null);


        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        userHomeFrame.add(sidebar);
        userHomeFrame.setVisible(true);

    }

    private void loadTabs() {
        homeTab = new HomeTab(this);
        myMoviesTab = new MyMoviesTab(this);
        searchMoviesTab = new SearchMoviesTab(this);
        viewFriendsTab = new ViewFriendsTab(this);

        sidebar.add(homeTab, 0);
        sidebar.setTitleAt(0, "Home");

        sidebar.add(myMoviesTab, 1);
        sidebar.setTitleAt(1, "My Movies");

        sidebar.add(viewFriendsTab, 2);
        sidebar.setTitleAt(2, "Friends");

        sidebar.add(searchMoviesTab, 3);
        sidebar.setTitleAt(3, "Search Movies");

    }

    //EFFECTS: saves all data to JSON files
    public void save() {
        JsonWriterMovieDatabase jsonWriterMovieDatabase = new JsonWriterMovieDatabase(JSON_MOVIES);
        JsonWriterUserDataStorage jsonWriterUserDataStorage = new JsonWriterUserDataStorage(JSON_USERS);
        try {
            jsonWriterMovieDatabase.open();
            jsonWriterMovieDatabase.write(database);
            jsonWriterMovieDatabase.close();
            JOptionPane.showMessageDialog(homeTab, "saved Database");
            System.out.println("Saved " + " to " + JSON_MOVIES);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_MOVIES);
        }
        try {
            jsonWriterUserDataStorage.open();
            jsonWriterUserDataStorage.write(allUsers);
            jsonWriterUserDataStorage.close();
            JOptionPane.showMessageDialog(homeTab, "saved user info");
            System.out.println("Saved " + " to " + JSON_USERS);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_USERS);
        }
    }

    //EFFECTS: JUST FOR TESTING, exits app
    public void exitAPP() {
        System.exit(0);
    }

    public User getUser() {
        return allUsers.returnUserFromDatabase(username);
    }

    public String getUsername() {
        return username;
    }

    public MovieDatabase getDatabase() {
        return database;
    }

    public UserDataStorage getAllUsers() {
        return allUsers;
    }
}
