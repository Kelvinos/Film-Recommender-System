package com.example.kelvin_pc.film.View;

import android.os.Bundle;

import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.R;
import com.example.kelvin_pc.film.View.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Rated extends BaseActivity {

    private ArrayList<Film> films;
    private HashMap<Film, Integer> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_rated);
        super.onCreate(savedInstanceState);

        // get rated data
        Bundle b = getIntent().getExtras();
        films = b.getParcelable("film");
    }




}
