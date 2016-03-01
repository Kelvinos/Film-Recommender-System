package com.example.kelvin_pc.film.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Controller.Maths_Handler;
import com.example.kelvin_pc.film.Model.Adapters.Recommend_Adapter;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;

import Jama.Matrix;

public class Recommendations extends BaseActivity implements AsyncResponse {

    private Maths_Handler mh;
    private Recommend_Adapter la;
    private ProgressDialog progress;

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
        Film_Downloader fd = new Film_Downloader();
        fd.delegate = this;
        fd.generateCategoryQuery("popular", Integer.toString(1));
        startProgressBar(progress, "Loading...");
    }

    @Override
    public void processFinish(ArrayList<Film> films) {
        Matrix labels = mh.generateTestData(films);
        ArrayList<Film> recommendedFilms = new ArrayList<>();
        for (int i=0; i<films.size(); i++) {
            if (labels.get(i, 0) == 1.0 && System_Variables.USER.getRating(films.get(i)) == 0) {
                recommendedFilms.add(films.get(i));
            }
        }
        stopProgressBar(progress);
        generateRecommendations(recommendedFilms);
        generateTitle(recommendedFilms.size());
    }

    public void generateRecommendations(final ArrayList<Film> films) {
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
            la = new Recommend_Adapter(this, films);
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
