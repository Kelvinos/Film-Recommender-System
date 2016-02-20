package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Account extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_account);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        initRecentlyRated();
        initRecommendations();
    }

    public void initRecentlyRated() {

    }

    public void initRecommendations() {

    }

    public void Rated(View view) {
        Intent intent = new Intent(this, Rated.class);
        startActivity(intent);
    }

    public void Recommendations(View view) {
        Intent intent = new Intent(this, Recommendations.class);
        startActivity(intent);
    }

}
