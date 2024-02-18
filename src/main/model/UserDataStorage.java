package model;

import java.util.ArrayList;

public class UserDataStorage {
    private ArrayList<MyMovieList> allUsers;


    //EFFECTS: creates an empty database of user's Personal Movie Lists
    public UserDataStorage() {
        allUsers = new ArrayList<>();
    }

    //EFFECTS: checks if user is in a list or not and returns the true
    public boolean isUserInDatabase(String username) {
        for (MyMovieList m : allUsers) {
            if (username.equals(m.getUsername())) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: checks if user is in a list or not and returns the user's myMoveList
    public MyMovieList isUserInDatabaseReturnUser(String username) {
        for (MyMovieList m : allUsers) {
            if (username.equals(m.getUsername())) {
                return m;
            }
        }
        return null;
    }

    //REQUIRES: user not already in database
    //EFFECTS: adds users list to allUsers
    public void addUserToDatabase(MyMovieList mml) {
        allUsers.add(mml);
    }

    //REQUIRES: the user be in allUsers
    //EFFECTS: returns the position of the user in allUsers
    public int getUserPosition(String username) {
        return allUsers.indexOf(isUserInDatabaseReturnUser(username));
    }


    //MODIFIES: this
    //EFFECTS: is user is in database, it overrides its data, if not, it adds the user to the database
    public void overrideUserData(MyMovieList mml) {
        if (isUserInDatabase(mml.getUsername())) {
            allUsers.set(getUserPosition(mml.getUsername()), mml);
        } else {
            addUserToDatabase(mml);
        }

    }


    //testing methods

    //EFFECTS: returns the total number of users
    public int getTotalUsers() {
        return allUsers.size();
    }

    //REQUIRES: i > 1
    //EFFECTS: returns the movie at a certain position
    public MyMovieList getUserOrder(int i) {
        return allUsers.get(i);
    }
}






