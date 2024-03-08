package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents and list of users that have an account
public class UserDataStorage implements Writable {
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


    //testing methods (getters)

    //EFFECTS: returns the total number of users
    public int getTotalUsers() {
        return allUsers.size();
    }

    //REQUIRES: i > 1
    //EFFECTS: returns the movie at a certain position
    public MyMovieList getUserOrder(int i) {
        return allUsers.get(i);
    }

    public List<MyMovieList> getUsers() {
        return Collections.unmodifiableList(allUsers);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (MyMovieList mml : allUsers) {
            jsonArray.put(mml.toJson());
        }
        return jsonArray;
    }
}






