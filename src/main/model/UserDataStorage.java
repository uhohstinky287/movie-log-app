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



    //testing methods

    public int getTotalUsers() {
        return allUsers.size();
    }

    //REQUIRES: i > 1
    public MyMovieList getUserOrder(int i) {
        return allUsers.get(i - 1);
    }
}






