package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {

    @SerializedName("tittle")
    private String tittle;

    @SerializedName("type")
    private String type;

    @SerializedName("review")
    private String reviewText;

    public Review(String tittle, String type, String reviewText) {
        this.tittle = tittle;
        this.type = type;
        this.reviewText = reviewText;
    }

    public String getTittle() {
        return tittle;
    }

    public String getType() {
        return type;
    }

    public String getReviewText() {
        return reviewText;
    }

    @Override
    public String toString() {
        return "Review{" +
                "tittle='" + tittle + '\'' +
                ", type='" + type + '\'' +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
