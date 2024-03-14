package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Iterator;

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
        String description = jsonObject.getString("description");
        String director = jsonObject.getString("director");
        JSONObject reviews = jsonObject.getJSONObject("reviews");
        Movie m = new Movie(name, year);
        m.setMovieDescription(description);
        m.setDirector(director);
        m.setReviews(reviewsToMap(reviews));
        md.addMovieToDatabase(m);
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
