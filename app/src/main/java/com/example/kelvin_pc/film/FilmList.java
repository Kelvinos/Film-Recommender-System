package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FilmList extends BaseActivity {

    private ArrayList<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);

        films = new ArrayList<>();
        films.add(new Film("Goodfellas", "Gangster Film", "Crime", "9"));
        films.add(new Film("Toy story", "Disney Pixar", "Animation", "8"));
        films.add(new Film("Avengers", "Superhero Movie", "Action", "7"));
        films.add(new Film("Shawshank Redemption", "Prison Movie", "Drama", "8"));
        films.add(new Film("Interstellar", "Space Exploration", "Sci-Fi", "9"));

        ListAdapter la = new ListAdapter(this, films);
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(FilmList.this, FilmDetails.class);
                Bundle b = new Bundle();
                b.putParcelable("film", films.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        }));
        lv.setAdapter(la);
    }


}
