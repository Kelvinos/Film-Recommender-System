package com.example.kelvin_pc.film.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;

public class Film_List extends BaseActivity implements AsyncResponse {

    private String genre, yearS, yearE, ratingS, ratingE, runS, runE;
    private ProgressDialog progress;
    private ListAdapter la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        System_Variables.PAGE_NUMBER = 1;
        getBundleData();
        generateFilmQuery();
    }

    public void generateFilmQuery() {
        updatePageText();
        updateButtons();
        Film_Downloader fa = new Film_Downloader();
        fa.delegate = this;
        fa.generateQuery(genre, yearS, ratingS, Integer.toString(System_Variables.PAGE_NUMBER));
        startProgressBar();
    }

    @Override
    public void processFinish(final ArrayList<Film> output) {
        stopProgressBar();
        generateFilmList(output);
    }

    public void generateFilmList(final ArrayList<Film> films) {
        final ListView lv = (ListView) findViewById(R.id.listView);
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
            la.addItems(new ArrayList<Film>());
            la.notifyDataSetChanged();
            la.addItems(films);
            la.notifyDataSetChanged();
            lv.setSelectionAfterHeaderView();
        }

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
        if (System_Variables.PAGE_NUMBER == 1) {
            b.setClickable(false);
            b.setVisibility(View.INVISIBLE);
        } else {
            b.setClickable(true);
            b.setVisibility(View.VISIBLE);
        }
    }

    public void updatePageText() {
        TextView tv = (TextView) findViewById(R.id.text_page);
        tv.setText("Page " + System_Variables.PAGE_NUMBER);
    }

    public void nextPage(View view) {
        System_Variables.PAGE_NUMBER = System_Variables.PAGE_NUMBER+ 1;
        generateFilmQuery();
    }

    public void previousPage(View view) {
        if (System_Variables.PAGE_NUMBER != 1)
            System_Variables.PAGE_NUMBER = System_Variables.PAGE_NUMBER - 1;
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

        public void addItems(ArrayList<Film> films) {
            this.films = films;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row, null);
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
            String tagString = films.get(position).getTag();
            int thresh = System_Variables.TAG_THRESH;
            if (tagString.length() > thresh) {
                tagString = tagString.substring(0, thresh) + " ...";
            }
            tag.setText(tagString);
        }

        public void generateImage(final int position) {
            final ImageView image = (ImageView) rowView.findViewById(R.id.image_poster);
            try {
                new Image_Downloader(image).execute(films.get(position).getImg());
            } catch (Exception e) {
                new Debugger().print(e.toString());
            }
        }

    }

}
