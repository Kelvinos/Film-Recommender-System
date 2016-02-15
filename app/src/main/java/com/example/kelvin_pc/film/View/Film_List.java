package com.example.kelvin_pc.film.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.FilmAPI;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.R;

import java.net.URL;
import java.util.ArrayList;

public class Film_List extends BaseActivity implements AsyncResponse {

    private String genre, yearS, yearE, ratingS, ratingE, runS, runE;
    private ProgressDialog progress;
    private ListAdapter la;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        getBundleData();
        generateFilmQuery();
    }


    public void generateFilmQuery() {
        updatePageText();
        updateButtons();
        FilmAPI fa = new FilmAPI();
        fa.delegate = this;
        fa.generateQuery(genre, yearS, ratingS, Integer.toString(page));
        startProgressBar();
    }

    @Override
    public void processFinish(final ArrayList<Film> output) {
        new Debugger().print("reached here");
        stopProgressBar();
        generateFilmList(output);
    }

    public void generateFilmList(final ArrayList<Film> films) {
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(Film_List.this, Film_Details.class);
                Bundle b = new Bundle();
                b.putParcelable("film", films.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        if (la == null) {
            la = new ListAdapter(this, films);
            lv.setAdapter(la);
        } else {
            runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    la.clear();
                    la.addAll(films);
                }
            }));

        }
        la.notifyDataSetChanged();
        lv.setSelectionAfterHeaderView();

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

    public void startProgressBar() {
        progress = new ProgressDialog(this);
        progress.setTitle("Loading...");
        progress.show();
    }

    public void stopProgressBar() {
        progress.dismiss();
    }

    public void updateButtons() {
        Button b = (Button) findViewById(R.id.button_previous);
        if (page == 1) {
            b.setClickable(false);
            b.setVisibility(View.INVISIBLE);
        } else {
            b.setClickable(true);
            b.setVisibility(View.VISIBLE);
        }
    }

    public void updatePageText() {
        TextView tv = (TextView) findViewById(R.id.text_page);
        tv.setText("Page " + page);
    }

    public void nextPage(View view) {
        this.page = page + 1;
        new Debugger().print(Integer.toString(page));
        generateFilmQuery();
    }

    public void previousPage(View view) {
        if (page != 1)
            this.page = page - 1;
        new Debugger().print(Integer.toString(page));
        generateFilmQuery();
    }

    public class ListAdapter extends ArrayAdapter<Film> {

        private final Context context;
        private ArrayList<Film> films;
        private View rowView;

        public ListAdapter(Context context, ArrayList<Film> films) {
            super(context, R.layout.row, films);
            this.context = context;
            this.films = films;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row, parent, false);
            generateTitle(position);
            generateTag(position);
            generateImage(position);
            return rowView;
        }

        public void generateTitle(final int position) {
            TextView title = (TextView) rowView.findViewById(R.id.text_title);
            title.setText(films.get(position).getTitle());
        }

        public void generateTag(final int position) {
            TextView tag = (TextView) rowView.findViewById(R.id.text_description);
            tag.setText(films.get(position).getTag());
        }

        public void generateImage(final int position) {
            final ImageView image = (ImageView) rowView.findViewById(R.id.image_poster);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(films.get(position).getImg());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        image.setImageBitmap(bmp);
                    } catch (Exception e) {
                        new Debugger().print(e.toString());
                    }
                }
            });
            thread.start();
        }

    }

}
