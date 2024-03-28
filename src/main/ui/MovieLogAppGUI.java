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

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel newUserLabel;


    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton confirmCreateAccountButton;
    private JButton returnToStartingScreenButton;
    private JButton forgotPasswordButton;
    private JPasswordField passwordConfirmationField;

    private JPanel createAccountPanel;
    private JPanel homeTab;
    private JPanel myMoviesTab;
    private JPanel searchMoviesTab;
    private JPanel viewFriendsTab;

    private ImageIcon welcomeBackImage;
    private ImageIcon creatingYourAccountImage;


    private JTabbedPane sidebar;
    private static int SCREEN_WIDTH = 1368;
    private static int SCREEN_HEIGHT = 912;
    private static int FRAME_WIDTH = SCREEN_WIDTH - 10;
    private static int FRAME_HEIGHT = SCREEN_HEIGHT - 50;

    private Color defaultColour;

    // runs the Movie Log App GUI
    public MovieLogAppGUI() throws FileNotFoundException {
        initializeApp();
        startingFrame();
    }

    //EFFECTS: initializes the application by loading the movie database/users and initili
    private void initializeApp() {
        loadDatabase();
        loadUsers();
        loadImages();
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

    //EFFECTS: loads the images from the image file
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        welcomeBackImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "welcomeBack.png");
        creatingYourAccountImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "creatingYourAccount.png");

    }


    //EFFECTS: creates the starting screen
    private void startingFrame() {
        startingMenuFrame = new JFrame("Start App");
        startingMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startingMenuFrame.setSize(450, 250);
        startingMenuFrame.setLocationRelativeTo(null);
        initializeStartingMenuPanel();
        startingMenuFrame.add(startingMenuPanel);
        startingMenuFrame.setVisible(true);
    }

    //EFFECTS: creates startingMenuPanel
    private void initializeStartingMenuPanel() {
        startingMenuPanel = new JPanel();
        startingMenuPanel.setLayout(null);
        usernameLabel = new JLabel("Username:", JLabel.CENTER);
        usernameLabel.setBounds(50, 30, 100, 25);
        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 200, 25);
        passwordLabel = new JLabel("Password:", JLabel.CENTER);
        passwordLabel.setBounds(50, 70, 100, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 200, 25);
        loginButton = new JButton("Login");
        loginButton.setBounds(260,100, 89,25);
        newUserLabel = new JLabel("New User?", JLabel.RIGHT);
        newUserLabel.setBounds(80,180, 100, 25);
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(210, 180, 139, 25);
        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(75, 100, 140, 25);

        defaultColour = startingMenuPanel.getBackground();
        addAllToStartingMenuPanel();
        loginScreenButtonsActions();
    }

    //EFFECTS: adds all labels, fields, and buttons to starting Menu Panel
    private void addAllToStartingMenuPanel() {
        startingMenuPanel.add(usernameLabel);
        startingMenuPanel.add(usernameField);
        startingMenuPanel.add(passwordLabel);
        startingMenuPanel.add(passwordField);
        startingMenuPanel.add(createAccountButton);
        startingMenuPanel.add(loginButton);
        startingMenuPanel.add(newUserLabel);
        startingMenuPanel.add(forgotPasswordButton);
    }

    //EFFECTS: implements the logic for the login buttons
    private void loginScreenButtonsActions() {
        loginButton.addActionListener(e ->  {
            username = usernameField.getText().toLowerCase();
            password = new String(passwordField.getPassword());
            loginMenu(username, password); });
        createAccountButton.addActionListener(e -> createAccountPanel());
        forgotPasswordButton.addActionListener(e ->
                JOptionPane.showMessageDialog(startingMenuFrame, "Have you tried remembering it?"));
    }

    //EFFECTS: checks if login is correct, if so goes to welcomeBack Screen
    private void loginMenu(String username, String password) {
        if (allUsers.isUserInDataBase(username)) {
            if (allUsers.returnUserFromDatabase(username).getPassword().equals(password)) {
                user = allUsers.returnUserFromDatabase(username);
                startingMenuFrame.setVisible(false);
                startingMenuFrame.dispose();
                welcomeBackScreen();
            } else {
                JOptionPane.showMessageDialog(startingMenuFrame, "Incorrect Password");
            }
        } else {
            JOptionPane.showMessageDialog(startingMenuFrame, "Username not in database");
        }
    }

    //EFFECTS: removes the starter panel and creates the accountPanel
    private void createAccountPanel() {
        startingMenuFrame.remove(startingMenuPanel);
        createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new GridLayout(5, 2));
        addCreateAccountFields();
        confirmCreateAccountButtonActions(confirmCreateAccountButton);
        returnToStartingScreenButtonActions(returnToStartingScreenButton);
    }

    //EFFECTS: adds the fields for the create account panel
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

    //EFFECTS: implements the logic for create account button
    private void confirmCreateAccountButtonActions(JButton confirmCreateAccountButton) {
        confirmCreateAccountButton.addActionListener(e -> {
            username =  usernameField.getText().toLowerCase();
            password = new String(passwordField.getPassword());
            String confirmPassword = new String(passwordConfirmationField.getPassword());

            if (allUsers.isUserInDataBase(username)) {
                JOptionPane.showMessageDialog(startingMenuFrame, "Username is taken");
            } else {
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(startingMenuFrame, "Passwords do not match");
                } else {
                    user = new User(username);
                    user.setPassword(password);
                    JOptionPane.showMessageDialog(startingMenuFrame, "Account created successfully!");
                    allUsers.addUserToDatabase(user);
                    startingMenuFrame.setVisible(false);
                    startingMenuFrame.dispose();
                    creatingYourAccountScreen();
                }
            }
        });
    }

    //EFFECTS: implements the logic for the return to starting screen button
    private void returnToStartingScreenButtonActions(JButton returnToStartingScreenButton) {
        returnToStartingScreenButton.addActionListener(e -> {
            startingMenuFrame.dispose();
            startingFrame();

        });
    }


    //EFFECTS: removes the starting screen, creates the welcome back screen , then goes to home screen
    private void welcomeBackScreen() {
        startingMenuFrame.dispose();
        startingMenuFrame.setVisible(false);
        JFrame welcomeBackFrame = new JFrame("Welcome Back");
        welcomeBackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeBackFrame.setSize(1000, 800);
        welcomeBackFrame.setLocationRelativeTo(null);
        JPanel welcomeBackPanel = new JPanel();
        welcomeBackPanel.setLayout(new GridLayout(0,1));
        JLabel welcomeBackLabel = new JLabel(welcomeBackImage);
        welcomeBackPanel.add(welcomeBackLabel);
        welcomeBackFrame.add(welcomeBackPanel);
        welcomeBackFrame.setVisible(true);
        Timer timer = new Timer(3000, e -> {
            welcomeBackFrame.dispose();
            homeScreen();
        });
        timer.setRepeats(false);
        timer.start();
    }

    //EFFECTS: removes starting screen, creates the creatingYourAccount screen, the goes to homescreen
    private void creatingYourAccountScreen() {
        startingMenuFrame.dispose();
        startingMenuFrame.setVisible(false);
        JFrame creatingYourAccountFrame = new JFrame("Welcome Back");
        creatingYourAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        creatingYourAccountFrame.setSize(1000, 800);
        creatingYourAccountFrame.setLocationRelativeTo(null);
        JPanel creatingYourAccountPanel = new JPanel();
        creatingYourAccountPanel.setLayout(new GridLayout(0,1));
        JLabel welcomeBackLabel = new JLabel(creatingYourAccountImage);
        creatingYourAccountPanel.add(welcomeBackLabel);
        creatingYourAccountFrame.add(creatingYourAccountPanel);
        creatingYourAccountFrame.setVisible(true);
        Timer timer = new Timer(3000, e -> {
            creatingYourAccountFrame.dispose();
            homeScreen();
        });
        timer.setRepeats(false);
        timer.start();
    }



    // EFFECTS: creates the home screen
    private void homeScreen() {

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


    //EFFECTS: loads all the tabs for the MovieLogAPP
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

    public Color getDefaultColour() {
        return defaultColour;
    }
}
