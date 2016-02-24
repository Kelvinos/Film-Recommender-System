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
    private Data_Handler dh;

    public User () {
        init();
    }

    public void init() {
        dh = new Data_Handler();
        ratings = new HashMap<>();
        readFromFile();
    }

    public void addRating(Film film, Integer rating) {
        if (rating == -1 || rating == 0 || rating == 1) {
            Film f = getFilm(film);
            ratings.put(f, rating);
            ratingUpdate();
        }
    }

    public void deleteRating(Film film) {
        Film f = getFilm(film);
        ratings.remove(f);
        ratingUpdate();
    }

    public void clearRatings() {
        ratings.clear();
        ratingUpdate();
    }

    public void ratingUpdate() {
        updated = true;
        writeToFile();
    }

    public Film getFilm(Film film) {
        try {
            for(Film f : ratings.keySet()) {
                if (f.getId() == film.getId()) {
                    return f;
                }
            }
        } catch (Exception e) {
            new Debugger().print("GET FILM", e.toString());
        }
        return film;
    }

    public int getRating(Film film) {
        try {
            for(Film f : ratings.keySet()) {
                if (f.getId() == film.getId()) {
                    return ratings.get(f);
                }
            }
        } catch (Exception e) {
            new Debugger().print("GET RATING", e.toString());
        }
        updated = true;
        return 0;
    }

    public int getGoodRatings() {
        int count = 0;
        try {
            for (Map.Entry<Film, Integer> entry : ratings.entrySet()) {
                if (entry.getValue() == 1) {
                    count ++;
                }
            }
        } catch (Exception e) {
            new Debugger().print("GET GOOD RATING", e.toString());
        }
        return count;
    }

    public void readFromFile() {
        HashMap<Film, Integer> r = dh.readData();
        if (r != null) {
            this.ratings = r;
        }
    }

    public void writeToFile() {
        dh.writeData(ratings);
    }

    public HashMap<Film, Integer> getRatings() {
        return ratings;
    }
    public boolean getUpdated() {
        return this.updated;
    }
    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
