package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Home extends BaseActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_home);
        super.onCreate(savedInstanceState);

        setSpinners();
        // Stop keyboard auto popping up at the start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // When the search button is pressed run this method
    public void SearchFilms(View view) {
        Intent intent = new Intent(this, FilmList.class);
        startActivity(intent);
    }


    public void setSpinners() {
        // Create genre spinner
        makeSpinner(R.id.spinner_genre, R.array.genre_array, 0);

        // Create year spinners
        makeSpinner(R.id.spinner_year_start, R.array.year_array,27);
        makeSpinner(R.id.spinner_year_end, R.array.year_array, 0);

        // Create rating spinners
        makeSpinner(R.id.spinner_rating_start, R.array.rating_array, 9);
        makeSpinner(R.id.spinner_rating_end, R.array.rating_array, 0);

        // Create runtime spinners
        makeSpinner(R.id.spinner_runtime_start, R.array.runtime_array, 6);
        makeSpinner(R.id.spinner_runtime_end, R.array.runtime_array, 0);
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
