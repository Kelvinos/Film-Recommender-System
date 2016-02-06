package com.example.kelvin_pc.film;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FilmList extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);

        ArrayList<Film> films = new ArrayList<>();
        films.add(new Film("Goodfellas", "american crime", "crime", "9"));
        films.add(new Film("Toy story", "disney animation", "animation", "8"));
        films.add(new Film("Avengers", "action fighting superheroes", "action", "7"));
        films.add(new Film("Toy story", "disney animation", "animation", "8"));
        films.add(new Film("Avengers", "action fighting superheroes", "action", "7"));

        ListAdapter la = new ListAdapter(this, films);
        ListView lv = (ListView) findViewById(R.id.listView);

        lv.setAdapter(la);
    }


}
