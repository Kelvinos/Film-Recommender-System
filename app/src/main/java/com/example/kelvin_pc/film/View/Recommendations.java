package com.example.kelvin_pc.film.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;
import com.example.kelvin_pc.film.Model.BaseActivity;
import java.util.HashMap;

public class Recommendations extends BaseActivity {

    private User user;
    private HashMap<Film, Integer> ratings;

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
    }

    public void displayError() {
        TextView t = (TextView) findViewById(R.id.text_error_recommendation);
        ListView l = (ListView) findViewById(R.id.list_recommendations);
        l.setVisibility(View.GONE);
        t.setText("No suggestions." + "\n" +  "Must have rated at least 10 good films and 10 bad films.");
    }
}
