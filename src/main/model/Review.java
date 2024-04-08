package model;

import org.json.JSONObject;
import persistence.Writable;

//represents a Review for a movie by a user
//TODO: NEW! create a test class
public class Review implements Writable {
    private String username;
    private String writtenReview;
    private int rating;


    //EFFECTS: creates a review with a rating, a username and a blank written review
    public Review(String username, Integer rating) {
        this.username = username;
        this.rating = rating;
        this.writtenReview = "";
    }

    //getters
    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getWrittenReview() {
        return writtenReview;
    }



    //setters
    public void setRating(Integer r) {
        this.rating = r;
        EventLog.getInstance().logEvent(new Event("added rating of " + r + "!"));
    }

    public void setWrittenReview(String wr) {
        this.writtenReview = wr;
        EventLog.getInstance().logEvent(new Event("Added written review!"));
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("rating", rating);
        json.put("writtenReview", writtenReview);
        return json;
    }
}
