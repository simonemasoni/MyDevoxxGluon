package com.devoxx.model;

import java.util.List;

public class Rating {

    private int rating;
    private List<RatingData> data;

    public Rating() {}

    public Rating(int rating, List<RatingData> data) {
        this.rating = rating;
        this.data = data;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<RatingData> getData() {
        return data;
    }

    public void setData(List<RatingData> data) {
        this.data = data;
    }
}
