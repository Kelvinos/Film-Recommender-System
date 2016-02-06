package com.example.kelvin_pc.film;


public class Film {

    private String title;
    private String description;
    private String genre;
    private String rating;

    public Film(String title, String description, String genre, String rating) {
        super();
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getRating() {
        return this.rating;
    }

    public String getGenre() {
        return this.genre;
    }
}
