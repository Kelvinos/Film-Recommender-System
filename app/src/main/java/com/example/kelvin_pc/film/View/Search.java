package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Model.BaseActivity;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.HashMap;

public class Search extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private Spinner genre, releaseS, releaseE, ratingS, ratingE, voteCountS, voteCountE;
    private HashMap<String, String> greaterLesserMap, categoryMap;
    private LinearLayout titleLayout, categoryLayout, discoverLayout;
    private String selected;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        initVariables();
        initTitleSection();
        initCategorySection();
        initDiscoverSection();
        selectTitleView(null);
    }

    public void initVariables() {
        if (System_Variables.USER == null) {
            System_Variables.USER = new User();
        }
        greaterLesserMap = new HashMap<>();
        greaterLesserMap.put("Greater", ".gte");
        greaterLesserMap.put("Lesser", ".lte");
        categoryMap = new HashMap<>();
        categoryMap.put("Now Playing", "now_playing");
        categoryMap.put("Popular", "popular");
        categoryMap.put("Top Rated", "top_rated");
        categoryMap.put("Upcoming", "upcoming");
    }

    public void initTitleSection() {
        title = (EditText) findViewById(R.id.text_title);
        titleLayout = (LinearLayout) findViewById(R.id.layout_title);
    }

    public void initCategorySection() {
        categoryLayout = (LinearLayout) findViewById(R.id.layout_category);
    }

    public void initDiscoverSection() {
        discoverLayout = (LinearLayout) findViewById(R.id.layout_discover);
        initSpinners();
    }

    public void initSpinners() {
        genre = makeSpinner(R.id.spinner_genre, R.array.genre_array, 0);
        releaseS = makeSpinner(R.id.spinner_year_start, R.array.release_date_array,27);
        releaseE = makeSpinner(R.id.spinner_year_end, R.array.greater_lesser_array, 0);
        ratingS = makeSpinner(R.id.spinner_rating_start, R.array.rating_array, 9);
        ratingE = makeSpinner(R.id.spinner_rating_end, R.array.greater_lesser_array, 0);
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

    public void unselectTitleLayout() {
        titleLayout.setBackgroundColor(Color.TRANSPARENT);
        title.setText("");
    }

    public void unselectCategoryLayout() {
        categoryLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void unselectDiscoverLayout() {
        discoverLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void selectTitleView(View view) {
        selected = "Title";
        titleLayout.setBackgroundColor(Color.LTGRAY);
        unselectCategoryLayout();
        unselectDiscoverLayout();
    }

    public void selectDiscoverView(View view) {
        selected = "Discover";
        discoverLayout.setBackgroundColor(Color.LTGRAY);
        unselectTitleLayout();
        unselectCategoryLayout();
    }

    public void selectCategoryView(View view) {
        selected = "Category";
        categoryLayout.setBackgroundColor(Color.LTGRAY);
        unselectTitleLayout();
        unselectDiscoverLayout();
    }

    public void SearchFilms(View view) {
        switch (selected) {
            case "Title":
                titleQuery();
                break;
            case "Category":
                categoryQuery();
                break;
            case "Discover":
                discoverQuery();
                break;
            default:
                break;
        }
    }

    public void titleQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra("Query", "Title");
        intent.putExtra("Title", title.getText().toString());
        startActivity(intent);
    }

    public void categoryQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra("Query", "Category");
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        intent.putExtra("Category", categoryMap.get(rb.getText().toString()));
        startActivity(intent);
    }

    public void discoverQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra("Query", "Discover");
        intent.putExtra(getString(R.string.genre), genre.getSelectedItem().toString());
        intent.putExtra(getString(R.string.release_date), releaseS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.release_date) + "x", greaterLesserMap.get((releaseE.getSelectedItem().toString())));
        intent.putExtra(getString(R.string.rating), ratingS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.rating) + "x", greaterLesserMap.get(ratingE.getSelectedItem().toString()));
        intent.putExtra(getString(R.string.vote_count), voteCountS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.vote_count) + "x", greaterLesserMap.get(voteCountE.getSelectedItem().toString()));
        startActivity(intent);
    }

}
