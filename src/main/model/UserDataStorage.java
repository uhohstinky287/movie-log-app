package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents and list of users that have an account
public class UserDataStorage implements Writable {
    private Map<String, User> allUsersMap;


    //EFFECTS: creates an empty database of user's Personal Movie Lists
    public UserDataStorage() {
        allUsersMap = new LinkedHashMap<>();
    }

    //getters

    //EFFECTS: returns the total number of users
    public int getTotalUsers() {
        return allUsersMap.keySet().size();
    }

    //EFFECTS: returns the list of users as an ArrayList
    public List<User> getUsers() {
        List<User> listOfUsers = new ArrayList<>(allUsersMap.values());
        return listOfUsers;
    }


    //setters
    public void setAllUsersMap(Map<String, User> allUsers) {
        this.allUsersMap = allUsers;
    }





    //EFFECTS: checks if user in the userDatabase and returns true or false
    public boolean isUserInDataBase(String username) {
        return allUsersMap.containsKey(username);
    }


    //REQUIRES: user not in database
    //EFFECT: checks if user is in the database or not and returns User
    public User returnUserFromDatabase(String username) {
        return allUsersMap.get(username);
    }


    //REQUIRES: user not in database
    //EFFECTS: adds user to user database
    public void addUserToDatabase(User u) {
        allUsersMap.put(u.getUsername(), u);
    }


    //MODIFIES:this
    //EFFECTS: is user is in database, it overrides its data, if not, it adds the user to the database
    public void overrideUserData(User u) {
        if (isUserInDataBase(u.getUsername())) {
            allUsersMap.replace(u.getUsername(), u);
        } else {
            addUserToDatabase(u);
        }
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }



    public JSONObject usersToJson() {
        JSONObject jsonHashMap = new JSONObject();
        for (String key : allUsersMap.keySet()) {
            jsonHashMap.put(key, allUsersMap.get(key).toJson());
        }
        return jsonHashMap;
    }
}






