package com.example.kelvin_pc.film.Controller;

import android.util.Log;

public class Debugger {
    public void print(String string) {
        Log.d("myTag", string);
    }
    public void print(String loc, String string) {Log.d("myTag " + loc, string);}
}
