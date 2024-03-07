package model;

public class Review {
    private String writtenReview;
    private String username;


    public Review(String username) {
        this.writtenReview = "";
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getWrittenReview() {
        return writtenReview;
    }

    public void setWrittenReview(String writtenReview) {
        this.writtenReview = writtenReview;
    }
}
