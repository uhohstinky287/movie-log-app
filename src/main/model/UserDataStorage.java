package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents and list of users that have an account
public class UserDataStorage implements Writable {
    private Map<String, User> allUsersMap;


    //EFFECTS: creates an empty database of user's Personal Movie Lists
    public UserDataStorage() {
//        allUsers = new ArrayList<>();
        allUsersMap = new LinkedHashMap<>();
    }


    //EFFECTS: checks if user in the userDatabase and returns true or false //TODO:NEW
    public boolean isUserInDataBaseLOL(String username) {
        return allUsersMap.containsKey(username);
    }

//    //EFFECTS: checks if user is in a list or not and returns the user's myMoveList
//    public User isUserInDatabaseReturnUser(String username) {
//        for (User m : allUsers) {
//            if (username.equals(m.getUsername())) {
//                return m;
//            }
//        }
//        return null;
//    }

    //REQUIRES: user not in database
    //EFFECT: checks if user is in the database or not and returns User //TODO: NEW
    public User returnUserFromDatabase(String username) {
        return allUsersMap.get(username);
    }


    //REQUIRES: user not in database
    //EFFECTS: adds user to user database
    public void addUserToDatabaseLOL(User u) { //TODO: NEW
        allUsersMap.put(u.getUsername(), u);
    }



//    //REQUIRES: the user be in allUsers
//    //EFFECTS: returns the position of the user in allUsers
//    public int getUserPosition(String username) {
//        return allUsers.indexOf(isUserInDatabaseReturnUser(username));
//    }


//    //MODIFIES: this
//    //EFFECTS: is user is in database, it overrides its data, if not, it adds the user to the database
//    public void overrideUserData(User u) {
//        if (isUserInDatabase(u.getUsername())) {
//            allUsers.set(getUserPosition(u.getUsername()), u);
//        } else {
//            addUserToDatabase(u);
//        }
//
//    }

    //MODIFIES:this
    //EFFECTS: is user is in database, it overrides its data, if not, it adds the user to the database //TODO:NEW
    public void overrideUserDataLOL(User u) {
        if (isUserInDataBaseLOL(u.getUsername())) {
            allUsersMap.replace(u.getUsername(), u);
        } else {
            addUserToDatabaseLOL(u);
        }

    }


    //testing methods (getters)


    //EFFECTS: returns the total number of users //TODO:NEW
    public int getTotalUsersLOL() {
        return allUsersMap.keySet().size();
    }


    //TODO:NEW
    public Map<String, User> getAllUsersMap() {
        return allUsersMap;
    }

    public void setAllUsersMap(Map<String, User> allUsers) {
        this.allUsersMap = allUsers;
    }

    //TODO:NEW
    public List<User> getUsersLOL() {
        List<User> listOfUsers = new ArrayList<>(allUsersMap.values());
        return listOfUsers;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJsonLOL()); //todo
        return json;
    }


    //TODO:NEW
    private JSONObject usersToJsonLOL() {
        JSONObject jsonHashMap = new JSONObject();
        for (String key : allUsersMap.keySet()) {
            jsonHashMap.put(key, allUsersMap.get(key).toJson());
        }
        return jsonHashMap;
    }
}






