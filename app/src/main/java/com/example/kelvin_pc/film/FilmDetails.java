package com.example.kelvin_pc.film;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;

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
        final ImageView image = (ImageView) findViewById(R.id.image_poster);
        title.setText(film.getTitle());
        description.setText(film.getDescription());
        genre.setText(film.getGenre());
        rating.setText(film.getRating());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("myTag", film.getImg());
                    URL url = new URL(film.getImg());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image.setImageBitmap(bmp);
                } catch (Exception e) {
                    Log.d("myTag", e.toString());
                }
            }
        });
        thread.start();

    }

    // Rate film good
    public void RateGood(View view) {
        film.setUserRating("1");
        LinearLayout g = (LinearLayout) findViewById(R.id.view_thumbs_up);
        LinearLayout b = (LinearLayout) findViewById(R.id.view_thumbs_down);
        g.setBackgroundColor(getResources().getColor(R.color.yellow));
        b.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    // Rate film bad
    public void RateBad(View view) {
        film.setUserRating("0");
        LinearLayout g = (LinearLayout) findViewById(R.id.view_thumbs_up);
        LinearLayout b = (LinearLayout) findViewById(R.id.view_thumbs_down);
        b.setBackgroundColor(getResources().getColor(R.color.yellow));
        g.setBackgroundColor(getResources().getColor(R.color.transparent));
    }


}
