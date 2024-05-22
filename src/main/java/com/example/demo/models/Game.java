package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Game {
     private double averageScore;
    private String description;
    private String gameName;
    private double gamePrice;
    private List<String> genreId;
    private List<String> platformId;
    private List<String> reviewId;
    private String traderId;

    public Game() {
        // Default constructor required for Firebase
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public List<String> getGenreId() {
        if (genreId == null) {
            genreId = new ArrayList<>();
        }
        return genreId;
    }

    public void setGenreId(List<String> genreId) {
        this.genreId = genreId;
    }

    public List<String> getPlatformId() {
        if (platformId == null) {
            platformId = new ArrayList<>();
        }
        return platformId;
    }

    public void setPlatformId(List<String> platformId) {
        this.platformId = platformId;
    }

    public List<String> getReviewId() {
        if (reviewId == null) {
            reviewId = new ArrayList<>();
        }
        return reviewId;
    }

    public void setReviewId(List<String> reviewId) {
        this.reviewId = reviewId;
    }

    public String getTraderId() {
        return traderId;
    }

    public void setTraderId(String traderId) {
        this.traderId = traderId;
    }
}
