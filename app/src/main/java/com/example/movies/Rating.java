package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private String ratingKp;

    public Rating(String ratingKp) {
        this.ratingKp = ratingKp;
    }

    public String getRatingKp() {
        return ratingKp;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "ratingKp=" + ratingKp +
                '}';
    }
}
