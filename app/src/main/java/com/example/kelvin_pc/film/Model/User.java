package com.example.kelvin_pc.film.Model;

import com.example.kelvin_pc.film.Model.Film;

import java.util.ArrayList;

public class User {

    private ArrayList<Film> rated;

    public User() {
        rated = new ArrayList<>();
    }

    public void addRatedFilm(Film film) {
        rated.add(film);
    }

    public boolean alreadyRated(Film film) {
        for (Film f : rated)
            if (f.equals(film))
                return true;
         return false;
    }


}
