package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that can read the UserDataStorage
public class JsonReaderUserDataStorage {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderUserDataStorage(String source) {
        this.source = source;
    }

    //EFFECTS: reads UserDataStorage from file and returns it, throws IOException if error reading from file
    public UserDataStorage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserDataStorage(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses userDataStorage from JSON object and returns it
    private UserDataStorage parseUserDataStorage(JSONObject jsonObject) {
        UserDataStorage uds = new UserDataStorage();
        addUsers(uds, jsonObject);
        return uds;
    }

    // MODIFIES: uds
    // EFFECTS: parses users from JSON object and adds them to userDataStorage
    private void addUsers(UserDataStorage uds, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(uds, nextUser);
        }
    }

    // MODIFIES: uds
    // EFFECTS: parses User from JSON object and adds it to UserDataStorage
    private void addUser(UserDataStorage uds, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        MyMovieList mml = new MyMovieList(username);
        mml.setPassword(password);
        addMovies(mml, jsonObject);
        uds.overrideUserData(mml);
    }

    //MODIFIES: mml, uds
    //EFFECTS: parses movies from JSON object and adds them to user
    private void addMovies(MyMovieList mml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("myMovies");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(mml, nextMovie);
        }
    }

    //MODIFIES: mml, uds
    //EFFECTS: parses movie from JSON object and adds it to myMovieList
    private void addMovie(MyMovieList mml, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int year = jsonObject.getInt("year");
        String description = jsonObject.getString("description");
        String director = jsonObject.getString("director");
        JSONObject reviews = jsonObject.getJSONObject("reviews");
        Movie m = new Movie(name, year);
        m.setMovieDescription(description);
        m.setDirector(director);
        m.setReviews(reviewsToMap(reviews));
        mml.addMovie(m);
    }


    //EFFECTS: converts reviews JSONObject to HashMap
    private Map<String, Review> reviewsToMap(JSONObject jsonObject) {
        Map<String, Review> reviewMap = new LinkedHashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject review = jsonObject.getJSONObject(key);
            reviewMap.put(key, jsonObjectToReview(review));
        }
        return reviewMap;
    }

    //EFFECTS: coverts review JSONObject to Review
    private Review jsonObjectToReview(JSONObject jsonObject) {
        int rating = jsonObject.getInt("rating");
        String username = jsonObject.getString("username");
        String writtenReview = jsonObject.getString("writtenReview");
        Review review = new Review(username, rating);
        review.setWrittenReview(writtenReview);
        return review;
    }

}
