package com.example.kelvin_pc.film;

import android.os.Bundle;
import android.widget.TextView;

public class FilmDetails extends BaseActivity {

    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_film_details);
        super.onCreate(savedInstanceState);

        // Get data
        Bundle b = getIntent().getExtras();
        film = b.getParcelable("film");

        // Use data to set the page
        TextView title = (TextView) findViewById(R.id.text_title);
        TextView description = (TextView) findViewById(R.id.text_description);
        TextView rating = (TextView) findViewById(R.id.text_rating);
        TextView genre = (TextView) findViewById(R.id.text_genre);
        title.setText(film.getTitle());
        description.setText(film.getDescription());
        genre.setText(film.getGenre());
        rating.setText(film.getRating());

    }

}
