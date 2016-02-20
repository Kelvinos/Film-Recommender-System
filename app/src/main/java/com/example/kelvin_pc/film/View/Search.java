package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.HashMap;

public class Search extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private Spinner genre, releaseS, releaseE, ratingS, ratingE, voteCountS, voteCountE;
    private HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        initVariables();
        initSpinners();
    }

    public void initVariables() {
        if (System_Variables.USER == null) {
            System_Variables.USER = new User();
        }
        map = new HashMap<>();
        map.put("Greater", ".gte");
        map.put("Lesser", ".lte");
    }

    public void initSpinners() {
        // Create genre spinner
        genre = makeSpinner(R.id.spinner_genre, R.array.genre_array, 0);
        // Create year spinners
        releaseS = makeSpinner(R.id.spinner_year_start, R.array.release_date_array,27);
        releaseE = makeSpinner(R.id.spinner_year_end, R.array.greater_lesser_array, 0);
        // Create rating spinners
        ratingS = makeSpinner(R.id.spinner_rating_start, R.array.rating_array, 9);
        ratingE = makeSpinner(R.id.spinner_rating_end, R.array.greater_lesser_array, 0);
        // Create runtime spinners
        voteCountS = makeSpinner(R.id.spinner_runtime_start, R.array.vote_count_array, 0);
        voteCountE = makeSpinner(R.id.spinner_runtime_end, R.array.greater_lesser_array, 0);
    }

    public Spinner makeSpinner(int id, int array, int sel) {
        Spinner spinner = (Spinner) findViewById(id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(sel);
        return spinner;
    }

    public String getMapString(String s) {
        return map.get(s);
    }

    public void SearchFilms(View view) {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra(getString(R.string.genre), genre.getSelectedItem().toString());

        intent.putExtra(getString(R.string.release_date), releaseS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.release_date) + "x", getMapString(releaseE.getSelectedItem().toString()));

        intent.putExtra(getString(R.string.rating), ratingS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.rating) + "x", getMapString(ratingE.getSelectedItem().toString()));

        intent.putExtra(getString(R.string.vote_count), voteCountS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.vote_count) + "x", getMapString(voteCountE.getSelectedItem().toString()));

        startActivity(intent);
    }


}
