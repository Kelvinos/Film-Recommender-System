package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Home extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private Spinner genre, yearS, yearE, ratingS, ratingE, runS, runE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_home);
        super.onCreate(savedInstanceState);

        setSpinners();
    }

    // When the search button is pressed run this method
    public void SearchFilms(View view) {
        Intent intent = new Intent(this, FilmList.class);
        intent.putExtra(getString(R.string.genre), genre.getSelectedItem().toString());
        intent.putExtra(getString(R.string.year_start), yearS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.year_end), yearE.getSelectedItem().toString());
        intent.putExtra(getString(R.string.rating_start), ratingS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.rating_end), ratingE.getSelectedItem().toString());
        intent.putExtra(getString(R.string.runtime_start), runS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.runtime_end), runE.getSelectedItem().toString());
        startActivity(intent);
    }

    public void setSpinners() {
        // Create genre spinner
        genre = makeSpinner(R.id.spinner_genre, R.array.genre_array, 0);

        // Create year spinners
        yearS = makeSpinner(R.id.spinner_year_start, R.array.year_array,27);
        yearE = makeSpinner(R.id.spinner_year_end, R.array.year_array, 0);

        // Create rating spinners
        ratingS = makeSpinner(R.id.spinner_rating_start, R.array.rating_array, 9);
        ratingE = makeSpinner(R.id.spinner_rating_end, R.array.rating_array, 0);

        // Create runtime spinners
        runS = makeSpinner(R.id.spinner_runtime_start, R.array.runtime_array, 6);
        runE = makeSpinner(R.id.spinner_runtime_end, R.array.runtime_array, 0);
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

}
