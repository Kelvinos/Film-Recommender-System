package com.example.kelvin_pc.film.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Controller.Maths_Handler;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;
import com.example.kelvin_pc.film.Model.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import Jama.Matrix;

public class Recommendations extends BaseActivity implements AsyncResponse {

    private User user;
    private HashMap<Film, Integer> ratings;
    private Matrix xtr, xts, ytr, yts;
    private Maths_Handler mh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_recommendations);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        initVariables();
        initSuggestions();
    }

    public void initVariables() {
        user = System_Variables.USER;
        ratings = user.getRatings();
        mh = new Maths_Handler();
    }

    public void initSuggestions() {
        generateTrainingData();
        generateTestData();
    }

    public void generateTrainingData() {
        ArrayList<Film> ratedFilms = new ArrayList<>(ratings.keySet());

        xtr = new Matrix(ratedFilms.size(), 3);
        xtr = mh.generateMatrix(xtr, ratedFilms);

        ytr = new Matrix(ratedFilms.size(), 1);
        ytr = mh.generateLabels(ytr, ratedFilms, ratings);

        new Debugger().printMatrix("TRAINING", xtr);
        new Debugger().printMatrix("LABELS", ytr);
    }

    public void generateTestData() {
        Film_Downloader fd = new Film_Downloader();
        fd.delegate = this;
        fd.generateCategoryQuery("popular", Integer.toString(1));
    }

    @Override
    public void processFinish(ArrayList<Film> films) {
        xts = new Matrix(20, 3);
        xts = mh.generateMatrix(xts, films);
        yts = mh.KNN(xtr, ytr, xts, 1);

        new Debugger().printMatrix("TEST", xts);
        new Debugger().printMatrix("LABELS", yts);
    }


    public void displayError() {
        TextView t = (TextView) findViewById(R.id.text_error_recommendation);
        ListView l = (ListView) findViewById(R.id.list_recommendations);
        l.setVisibility(View.GONE);
        t.setText(R.string.suggestions_invalid);
    }

}
