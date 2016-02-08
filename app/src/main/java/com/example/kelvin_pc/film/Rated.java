package com.example.kelvin_pc.film;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
