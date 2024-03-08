package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// represents a reader that can read the AllMoviesList from JSON data
public class JsonReaderMovieDatabase {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderMovieDatabase(String source) {
        this.source = source;
    }

    //EFFECTS: reads allMoviesList from file and returns it, throws IOException if error reading from file
    public MovieDatabase read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMovieDatabase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses movieDatabase from JSON object and returns it
    private MovieDatabase parseMovieDatabase(JSONObject jsonObject) {
        MovieDatabase md = new MovieDatabase();
        addMovies(md, jsonObject);
        return md;
    }

    // MODIFIES: md
    // EFFECTS: parses movies from JSON object and adds them to movieDatabase
    private void addMovies(MovieDatabase md, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("movies");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(md, nextMovie);
        }
    }

    // MODIFIES: md
    // EFFECTS: parses movie from JSON object and adds it to movieDatabase
    private void addMovie(MovieDatabase md, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int year = jsonObject.getInt("year");
        int rating = jsonObject.getInt("rating");
        String description = jsonObject.getString("description");
        String director = jsonObject.getString("director");
        JSONArray allRatings = jsonObject.getJSONArray("allRatings");
        Movie m = new Movie(name, year);
        m.setUserRating(rating);
        m.setMovieDescription(description);
        m.setDirector(director);
        m.setTotalRatings(allRatingsToList(allRatings));
        md.addMovieToDatabase(m);
    }

    //EFFECTS: converts allRatingsList from JSONArray to ArrayList
    private ArrayList allRatingsToList(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        for (Object o: jsonArray) {
            arrayList.add(o);
        }
        return arrayList;
    }
}
