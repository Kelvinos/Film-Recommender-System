package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.R;

public class Film_Details extends BaseActivity {

    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_film_details);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        getBundleData();
        generatePage();
    }

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        film = b.getParcelable("film");
    }

    public void generatePage() {
        generateTitle();
        generateDescription();
        generateRating();
        generateGenre();
        generateRuntime();
        generateReleaseDate();
        generatePoster();
    }

    public void generateTitle() {
        TextView title = (TextView) findViewById(R.id.text_title);
        title.setText(film.getTitle());
    }

    public void generateDescription() {
        TextView description = (TextView) findViewById(R.id.text_description);
        String desc = film.getDescription();
        int thresh = System_Variables.DESC_THRESH;
        if (desc.length() > thresh) {
            desc = desc.substring(0, thresh) + " ...";
        }
        description.setText(desc);
    }

    public void generateRating() {
        TextView rating = (TextView) findViewById(R.id.text_rating);
        rating.setText(film.getRating());
    }

    public void generateGenre() {
        TextView genre = (TextView) findViewById(R.id.text_genre);
        genre.setText(film.getGenre());
    }

    public void generateRuntime() {
        TextView runtime = (TextView) findViewById(R.id.text_runtime);
        runtime.setText(film.getRunTime());
    }

    public void generateReleaseDate() {
        TextView releaseDate = (TextView) findViewById(R.id.text_year);
        releaseDate.setText(film.getReleaseDate());
    }

    public void generatePoster() {
        final ImageView image = (ImageView) findViewById(R.id.image_poster);
        try {
            new Image_Downloader(image).execute(film.getImg());
        } catch (Exception e) {
            new Debugger().print(e.toString());
        }
    }

    public void expandDescription(View view) {
        Intent intent = new Intent(this, Description.class);
        intent.putExtra("Description", film.getDescription());
        startActivity(intent);
    }

    public void RateGood(View view) {
        film.setUserRating("1");
        LinearLayout g = (LinearLayout) findViewById(R.id.view_thumbs_up);
        LinearLayout b = (LinearLayout) findViewById(R.id.view_thumbs_down);
        g.setBackgroundColor(getResources().getColor(R.color.yellow));
        b.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public void RateBad(View view) {
        film.setUserRating("0");
        LinearLayout g = (LinearLayout) findViewById(R.id.view_thumbs_up);
        LinearLayout b = (LinearLayout) findViewById(R.id.view_thumbs_down);
        b.setBackgroundColor(getResources().getColor(R.color.yellow));
        g.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


}
