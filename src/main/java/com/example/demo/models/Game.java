package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Game  extends DatabaseModel {
    private double AverageScore;
    private String Description;
    private String GameName;
    private double GamePrice;
    private List<String> GenreId;
    private List<String> PlatformId;
    private List<String> ReviewId;
    private String TraderId;

    public double getAverageScore() {
        return AverageScore;
    }

    public void setAverageScore(double averageScore) {
        AverageScore = averageScore;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public double getGamePrice() {
        return GamePrice;
    }

    public void setGamePrice(double gamePrice) {
        GamePrice = gamePrice;
    }

    public List<String> getGenreId() {
        return GenreId;
    }

    public void setGenreId(List<String> genreId) {
        GenreId = genreId;
    }

    public List<String> getPlatformId() {
        return PlatformId;
    }

    public void setPlatformId(List<String> platformId) {
        PlatformId = platformId;
    }

    public List<String> getReviewId() {
        return ReviewId;
    }

    public void setReviewId(List<String> reviewId) {
        ReviewId = reviewId;
    }

    public String getTraderId() {
        return TraderId;
    }

    public void setTraderId(String traderId) {
        TraderId = traderId;
    }

    public Game() {
        super();
    }





}
