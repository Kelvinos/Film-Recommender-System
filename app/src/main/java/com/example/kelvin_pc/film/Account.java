package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Account extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_account);
        super.onCreate(savedInstanceState);

        //set up recently rated list
        recentlyRatedList();

        //set up recommendations list
        recommendationsList();
    }

    public void recentlyRatedList() {
        ArrayList<String> ratedItems = new ArrayList<>();
        ratedItems.add("Film 1 - Good");
        ratedItems.add("Film 2 - Bad");
        ratedItems.add("Film 3 - Good");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ratedItems);
        ListView lv = (ListView) findViewById(R.id.list_recently_rated);
        lv.setAdapter(adapter);
    }

    public void recommendationsList() {
        ArrayList<String> recommendations = new ArrayList<>();
        recommendations.add("Film 7");
        recommendations.add("Film 8");
        recommendations.add("Film 9");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recommendations);
        ListView lv = (ListView) findViewById(R.id.list_recommendations);
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
