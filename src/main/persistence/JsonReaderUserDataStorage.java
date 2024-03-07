package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that can read the AllMoviesList
public class JsonReaderUserDataStorage {
    private String source;

    public JsonReaderUserDataStorage(String source) {
        this.source = source;
    }

    //EFFECTS: reads allMoviesList from file and returns it, throws IOException if error reading from file
    public UserDataStorage read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserDataStorage(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    private UserDataStorage parseUserDataStorage(JSONObject jsonObject) {
        UserDataStorage uds = new UserDataStorage();
        addUsers(uds, jsonObject);
        return uds;
    }


    private void addUsers(UserDataStorage uds, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");
        for (Object json : jsonArray) {
            JSONObject nextUser = (JSONObject) json;
            addUser(uds, nextUser);
        }
    }

    private void addUser(UserDataStorage uds, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        MyMovieList mml = new MyMovieList(username);
        addMovies(mml, jsonObject);
        uds.overrideUserData(mml);
    }


    private void addMovies(MyMovieList mml, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("myMovies");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(mml, nextMovie);
        }
    }

    private void addMovie(MyMovieList mml, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int year = jsonObject.getInt("year");
        int rating = jsonObject.getInt("rating");
        String description = jsonObject.getString("description");
        String director = jsonObject.getString("director");
        Movie m = new Movie(name, year);
        m.setUserRating(rating);
        m.setMovieDescription(description);
        m.setDirector(director);
        mml.addMovie(m);
    }

}
