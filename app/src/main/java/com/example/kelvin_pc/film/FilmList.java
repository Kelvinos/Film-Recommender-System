package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class FilmList extends BaseActivity implements AsyncResponse  {

    private String genre, yearS, yearE, ratingS, ratingE, runS, runE;
    private ArrayList<Film> films;
    private FilmAPI fa = new FilmAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);

        getBundleData();

        Log.d("myTag", genre + " " + yearS + " " + yearE + " " + ratingS + " " + ratingE + " " + runS + " " + runE);

        fa.delegate = this;
        fa.generateQuery(genre, yearS, ratingS);

    }

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        genre = b.getString(getString(R.string.genre));
        yearS = b.getString(getString(R.string.year_start));
        yearE = b.getString(getString(R.string.year_end));
        ratingS = b.getString(getString(R.string.rating_start));
        ratingE = b.getString(getString(R.string.rating_end));
        runS = b.getString(getString(R.string.runtime_start));
        runE = b.getString(getString(R.string.runtime_end));
    }

    @Override
    public void processFinish(ArrayList<Film> output) {
        Log.d("myTag", "Reached here");
        films = output;
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


//films = new ArrayList<>();
//        films.add(new Film("Goodfellas", "Gangster Film", "Crime", "9"));
//        films.add(new Film("Toy story", "Disney Pixar", "Animation", "8"));
//        films.add(new Film("Avengers", "Superhero Movie", "Action", "7"));
//        films.add(new Film("Shawshank Redemption", "Prison Movie", "Drama", "8"));
//        films.add(new Film("Interstellar", "Space Exploration", "Sci-Fi", "9"));
