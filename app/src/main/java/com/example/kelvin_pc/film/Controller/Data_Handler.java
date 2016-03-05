package com.example.kelvin_pc.film.Controller;

import com.example.kelvin_pc.film.Model.Film;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Data_Handler {

    private final String RATINGS = "storage/sdcard1/ratings.txt";
    private final String REC_FILMS = "storage/sdcard1/recommended.txt";

    public Data_Handler() {

    }

    public HashMap<Film, Integer> readRatings() {
        File file = new File(RATINGS);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<Film, Integer> ratings = (HashMap<Film, Integer>) ois.readObject();
            ois.close();
            fis.close();
            return ratings;
        } catch (Exception e) {
            new Debugger().print(e.toString());
            return null;
        }
    }

    public void writeRatings(HashMap<Film, Integer> ratings) {
        File file = new File(RATINGS);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ratings);
            oos.close();
            fos.close();
        } catch (Exception e) {
            new Debugger().print(e.toString());
        }
    }

    public void writeFilms(ArrayList<Film> films) {
        File file = new File(REC_FILMS);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(films);
            oos.close();
            fos.close();
        } catch (Exception e) {
            new Debugger().print(e.toString());
        }
    }

    public ArrayList<Film> readFilms() {
        File file = new File(REC_FILMS);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Film> films = (ArrayList<Film>) ois.readObject();
            ois.close();
            fis.close();
            return films;
        } catch (Exception e) {
            new Debugger().print(e.toString());
            return null;
        }
    }


}
