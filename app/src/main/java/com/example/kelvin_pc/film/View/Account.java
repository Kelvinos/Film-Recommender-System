package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kelvin_pc.film.R;

import java.util.ArrayList;

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
        ArrayList<String> ratedItems = new ArrayList<>();
        ratedItems.add("Film 1 - Good");
        ratedItems.add("Film 2 - Bad");
        ratedItems.add("Film 3 - Good");
        ratedItems.add("Film 1 - Good");
        ratedItems.add("Film 2 - Bad");
        ratedItems.add("Film 3 - Good");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list, ratedItems);
        ListView lv = (ListView) findViewById(R.id.list_recently_rated);
        lv.setVerticalScrollBarEnabled(false);
        lv.setFocusable(false);
        lv.setAdapter(adapter);
    }

    public void initRecommendations() {
        // Films from film list
       // Bundle b = getIntent().getExtras();
       // ArrayList<Film> films = b.getParcelable("film_array");
        ArrayList<String> recommendations = new ArrayList<>();
        recommendations.add("Film 7");
        recommendations.add("Film 8");
        recommendations.add("Film 9");
        recommendations.add("Film 7");
        recommendations.add("Film 8");
        recommendations.add("Film 9");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list, recommendations);
        ListView lv = (ListView) findViewById(R.id.list_recommendations);
        lv.setVerticalScrollBarEnabled(false);
        lv.setFocusable(false);
        lv.setAdapter(adapter);
    }

    public void Logout(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void Profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
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
