package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

public class Film_Details extends BaseActivity {

    private Film film;
    private LinearLayout g, b;
    private User u;

    @Override
    public void onResume() {
        super.onResume();
        updateUserRating();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_film_details);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        generateVariables();
        getBundleData();
        generatePage();
    }

    public void generateVariables() {
        g = linearLayout(R.id.view_thumbs_up);
        b = linearLayout(R.id.view_thumbs_down);
        u = System_Variables.USER;
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
        updateUserRating();
    }

    public void generateTitle() {
        TextView title = textView(R.id.text_title);
        title.setText(film.getTitle());
    }

    public void generateDescription() {
        TextView desc = textView(R.id.text_description);
        String d = film.getDescription();
        int thresh = System_Variables.DESC_THRESH;
        if (d.length() > thresh) {
            d = d.substring(0, thresh) + " ...";
        }
        desc.setText(d);
    }

    public void generateRating() {
        TextView rating = textView(R.id.text_rating);
        rating.setText(film.getRating());
    }

    public void generateGenre() {
        TextView genre = textView(R.id.text_genre);
        genre.setText(film.getGenre());
    }

    public void generateRuntime() {
        TextView runtime = textView(R.id.text_runtime);
        runtime.setText(film.getRunTime());
    }

    public void generateReleaseDate() {
        TextView releaseDate = textView(R.id.text_year);
        releaseDate.setText(film.getReleaseDate());
    }

    public void generatePoster() {
        final ImageView image = imageView(R.id.image_poster);
        new Image_Downloader(image).execute(film.getBackdrop());
    }

    public void setRatedGood() {
        setNoRating();
        g.setBackgroundColor(Color.GREEN);
    }

    public void setRatedBad() {
        setNoRating();
        b.setBackgroundColor(Color.RED);
    }

    public void setNoRating() {
        g.setBackgroundResource(R.color.transparent);
        b.setBackgroundResource(R.color.transparent);
    }

    public void RateGood(View view) {
        u.addRating(film, 1);
        updateUserRating();
    }

    public void RateBad(View view) {
        u.addRating(film, -1);
        updateUserRating();
    }

    public void expandDescription(View view) {
        Intent intent = new Intent(this, Description.class);
        intent.putExtra("Description", film.getDescription());
        startActivity(intent);
    }

    public void updateUserRating() {
        int rating = u.getRating(film);
        switch (rating) {
            case -1:
                setRatedBad();
                break;
            case 0:
                setNoRating();
                break;
            case 1:
                setRatedGood();
                break;
        }
    }

}
