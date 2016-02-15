package com.example.kelvin_pc.film.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.R;

import java.net.URL;

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
        description.setText(film.getDescription());
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(film.getImg());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image.setImageBitmap(bmp);
                } catch (Exception e) {
                }
            }
        });
        thread.start();
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
