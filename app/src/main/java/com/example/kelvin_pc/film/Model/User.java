package com.example.kelvin_pc.film.Model;

import com.example.kelvin_pc.film.Controller.Data_Handler;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private HashMap<Film, Integer> ratings;
    private boolean updated;

    public User () {
        init();
    }

    public void init() {
        ratings = new HashMap<>();
        readFromFile();
    }

    public void clearRatings() {
        ratings.clear();
        writeToFile();
        updated = true;
    }

    public boolean getUpdated() {
        return this.updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public void addRating(Film film, Integer rating) {
        if (rating == -1 || rating == 0 || rating == 1) {
            if (ratingExists(film)) {
                Film f = getFilm(film);
                ratings.put(f, rating);
            } else {
                ratings.put(film, rating);
            }
        }
        writeToFile();
    }

    public boolean ratingExists(Film film) {
        for(Film f : ratings.keySet()) {
            if (f.getId() == film.getId()) {
                updated = true;
                return true;
            }
        }
        return false;
    }

    public Film getFilm(Film film) {
        for(Film f : ratings.keySet()) {
            if (f.getId() == film.getId()) {
                return f;
            }
        }
        return null;
    }

    public int getRating(Film film) {
        for(Film f : ratings.keySet()) {
            if (f.getId() == film.getId()) {
                return ratings.get(f);
            }
        }
        return 0;
    }

    public void readFromFile() {
        Data_Handler dh = new Data_Handler();
        HashMap<Film, Integer> r = dh.readData();
        if (r != null) {
            this.ratings = r;
        }
    }

    public void writeToFile() {
        Data_Handler dh = new Data_Handler();
        dh.writeData(ratings);
    }

    public HashMap<Film, Integer> getRatings() {
        return ratings;
    }

}
