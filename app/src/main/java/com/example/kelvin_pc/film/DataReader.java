package com.example.kelvin_pc.film;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class DataReader {

    private String path = "storage/sdcard1/users.txt";
    private HashMap<String, String> users;

    public DataReader() {
        File file = new File(path);
        users = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("/");
                users.put(parts[0], parts[1]);
            }
            br.close();
        } catch (Exception e) {
            Log.d("mytag", e.toString());
        }
    }

    public String getPass(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }


}
