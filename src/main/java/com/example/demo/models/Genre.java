package com.example.demo.models;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class Genre extends DatabaseModel{
    @NotBlank(message = "Genre name is mandatory")
    @Size(min = 3, message = "Genre name must be at least 3 characters")
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
