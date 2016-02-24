package com.example.kelvin_pc.film.Controller;

import com.example.kelvin_pc.film.Model.Film;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Data_Handler {

    private final String PATH = "storage/sdcard1/ratings.txt";

    public Data_Handler() {

    }

    public HashMap<Film, Integer> readData() {
        File file = new File(PATH);
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

    public void writeData(HashMap<Film, Integer> ratings) {
        File file = new File(PATH);
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


}
