package com.example.kelvin_pc.film.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Data_Handler;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Controller.Maths_Handler;
import com.example.kelvin_pc.film.Model.Adapters.Recommend_Adapter;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Jama.Matrix;

public class Recommendations extends BaseActivity implements AsyncResponse {

    private Maths_Handler mh;
    private Recommend_Adapter la;
    private ProgressDialog progress;

    private int page = 1;
    private ArrayList<Film> films = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_recommendations);
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void init() {
        initVariables();
    }

    public void initVariables() {
        progress = new ProgressDialog(this);
        mh = new Maths_Handler(System_Variables.USER.getRatings());
    }

    public void getRecommendations() {

        mh.generateTrainingData();

        Data_Handler dh = new Data_Handler();
        ArrayList<Film> films = dh.readFilms();
        if (films == null) {
            new Debugger().print("!");
        } else {
            new Debugger().print(Integer.toString(films.size()));
        }

        Matrix labels = mh.generateTestData(films);

        HashMap<Film, Double> recommendedFilms = new HashMap<>();
        for (int i=0; i<films.size(); i++) {
            // Check if the recommended film label is 1 and that the user hasn't already seen the film
            if (labels.get(i, 0) == 1.0 && System_Variables.USER.getRating(films.get(i)) == 0) {
                recommendedFilms.put(films.get(i), labels.get(i, 1));
            }
        }

        generateRecommendations(recommendedFilms);
        generateTitle(recommendedFilms.size());
    }

    @Override
    public void processFinish(final ArrayList<Film> fl) {
        ArrayList<Film> f = new ArrayList<>();

        if (fl != null) {
            for (int i=0; i<fl.size(); i++) {
                try {
                    if (fl.get(i) != null) {
                        f.add(fl.get(i));
                        new Debugger().print(Integer.toString(i), fl.get(i).getTitle());
                    }
                } catch (Exception e) {
                    new Debugger().print("PRINTING TITLE", e.toString());
                }
            }
        }

        try {
            this.films.addAll(f);
        } catch (Exception e) {
            new Debugger().print("ADDING FILMS", e.toString());
        }

        new Debugger().print("NUMBER OF POPULAR FILMS ADDED:", Integer.toString(this.films.size()));

        try {
            Thread.sleep(6000);
        } catch (Exception e) {}

        if (page < 100) {
            page = page + 1;
            getRecommendations();
        } else {
            if (this.films.size() == 1000) {
                Data_Handler dh = new Data_Handler();
                dh.writeFilms(this.films);
            }
        }
    }

    public void generateRecommendations(final HashMap<Film, Double> recFilms) {
        final Map<Film, Double> sorted = sortByValues(recFilms);
        final ArrayList<Film> films = new ArrayList<>(sorted.keySet());
        final ArrayList<Double> weights = new ArrayList<>(sorted.values());

        final ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(Recommendations.this, Film_Details.class);
                Bundle b = new Bundle();
                b.putParcelable("film", films.get(position));
                intent.putExtras(b);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
            la = new Recommend_Adapter(this, films, weights);
            lv.setAdapter(la);
    }

    public void update() {
        if (la != null)
            la.notifyDataSetChanged();
    }

    public void refresh(View view) {
        getRecommendations();
    }

    public void generateTitle(int size) {
        TextView title = (TextView) findViewById(R.id.text_number_suggestions);
        title.setText(Integer.toString(size));
    }

}


