package com.example.demo.models;

public class Genre extends DatabaseModel{
    private String GenreName;

    public String getGenreName() {
        return GenreName;
    }

    public void setGenreName(String genreName) {
        this.GenreName = genreName;
    }

    public Genre(){
        super();
    }
}
