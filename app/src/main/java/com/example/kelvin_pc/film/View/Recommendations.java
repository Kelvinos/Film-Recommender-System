package com.example.kelvin_pc.film.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
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
    }

    public void initSuggestions() {
        if (ratings.size() < 20) {
            displayError();
        }
        generateTrainingData();
        generateTestData();
    }

    public void generateTrainingData() {
        ArrayList<Film> ratedFilms = new ArrayList<>(ratings.keySet());
        xtr = new Matrix(ratedFilms.size(), 3);
        generateMatrix(xtr, ratedFilms);

        ytr = new Matrix(ratedFilms.size(), 1);
        generateLabels(ytr, ratedFilms);

        new Debugger().printMatrix("TRAINING", xtr);
        new Debugger().printMatrix("LABELS", ytr);
    }

    public void generateTestData() {
        // 100 Popular films
        Film_Downloader fd = new Film_Downloader();
        fd.delegate = this;
        fd.generateCategoryQuery("popular", Integer.toString(1));
    }

    @Override
    public void processFinish(ArrayList<Film> films) {
        xts = new Matrix(20, 3);
        generateMatrix(xts, films);
        new Debugger().printMatrix("TEST", xts);

    }

    public void generateMatrix(Matrix m, ArrayList<Film> films) {
        for (int i=0; i<m.getRowDimension(); i++) {
            Film f = films.get(i);
            m.set(i, 0, Double.parseDouble(f.getRating()));
            m.set(i, 1, Double.parseDouble(f.getRunTime()));
            m.set(i, 2, Double.parseDouble(f.getReleaseDate().substring(0, 4)));
        }
    }

    public void generateLabels(Matrix m, ArrayList<Film> films) {
        for (int i=0; i<m.getRowDimension(); i++) {
            Film f = films.get(i);
            Double label =  1.0 * ratings.get(f);
            m.set(i, 0, label);
        }
    }

    public void displayError() {
        TextView t = (TextView) findViewById(R.id.text_error_recommendation);
        ListView l = (ListView) findViewById(R.id.list_recommendations);
        l.setVisibility(View.GONE);
        t.setText("No suggestions." + "\n" +  "Must have rated at least 10 good films and 10 bad films.");
    }

}
