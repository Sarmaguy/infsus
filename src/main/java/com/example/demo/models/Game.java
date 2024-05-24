package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Game  extends DatabaseModel {
    private double AverageScore;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String Description;

    @NotBlank(message = "Game name is mandatory")
    @Size(min = 3, message = "Game name must be at least 3 characters")
    private String GameName;
    @Min(value = 0, message = "Price must be positive")
    private double GamePrice;
    @NotEmpty(message = "At least one genre is required")
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
