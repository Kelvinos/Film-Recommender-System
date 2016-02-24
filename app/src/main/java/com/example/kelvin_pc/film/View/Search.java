package com.example.kelvin_pc.film.View;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kelvin_pc.film.Model.BaseActivity;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.HashMap;

public class Search extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private Spinner genre, releaseS, releaseE, ratingS, ratingE, voteCountS, voteCountE;
    private HashMap<String, String> greaterLesserMap, categoryMap;
    private LinearLayout titleLayout, categoryLayout, discoverLayout, titleTitleLayout, categoryTitleLayout, discoverTitleLayout;
    private ImageView discoverToggle, categoryToggle, titleToggle;
    private String selected;
    private EditText title;
    private ImageButton delete;

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
        initView();
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
        titleTitleLayout = (LinearLayout) findViewById(R.id.layout_title_title);
        titleToggle = (ImageView) findViewById(R.id.image_title_toggle);
        delete = (ImageButton) findViewById(R.id.image_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title.setText(null);
                delete.setVisibility(View.GONE);
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                delete.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initCategorySection() {
        categoryLayout = (LinearLayout) findViewById(R.id.layout_category);
        categoryTitleLayout = (LinearLayout) findViewById(R.id.layout_category_title);
        categoryToggle = (ImageView) findViewById(R.id.image_category_toggle);
    }

    public void initDiscoverSection() {
        discoverLayout = (LinearLayout) findViewById(R.id.layout_discover);
        discoverTitleLayout = (LinearLayout) findViewById(R.id.layout_discover_title);
        discoverToggle = (ImageView) findViewById(R.id.image_discover_toggle);
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

    public void initView() {
        toggleOff(null);
        toggleDiscover(null);
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
        titleTitleLayout.setBackgroundColor(Color.TRANSPARENT);
        title.setCursorVisible(false);
        title.setText(null);
        delete.setVisibility(View.GONE);
    }

    public void unselectCategoryLayout() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        rg.clearCheck();
        categoryLayout.setBackgroundColor(Color.TRANSPARENT);
        categoryTitleLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void unselectDiscoverLayout() {
        discoverLayout.setBackgroundColor(Color.TRANSPARENT);
        discoverTitleLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void selectTitleView(View view) {
        selected = "Title";
        titleLayout.setBackgroundColor(getResources().getColor(R.color.light_grey));
        titleTitleLayout.setBackgroundColor(getResources().getColor(R.color.darker_grey));
        title.setCursorVisible(true);
        unselectCategoryLayout();
        unselectDiscoverLayout();
        displayError("");
    }

    public void selectDiscoverView(View view) {
        selected = "Discover";
        discoverLayout.setBackgroundColor(getResources().getColor(R.color.light_grey));
        discoverTitleLayout.setBackgroundColor(getResources().getColor(R.color.darker_grey));
        unselectTitleLayout();
        unselectCategoryLayout();
        closeKeyboard();
        displayError("");
    }

    public void selectCategoryView(View view) {
        selected = "Category";
        categoryLayout.setBackgroundColor(getResources().getColor(R.color.light_grey));
        categoryTitleLayout.setBackgroundColor(getResources().getColor(R.color.darker_grey));
        unselectTitleLayout();
        unselectDiscoverLayout();
        closeKeyboard();
        displayError("");
    }

    public void toggleDiscover(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_discover_body);
        selectDiscoverView(null);
        toggleBody(l, discoverToggle);
        toggleOff(l);
    }

    public void toggleTitle(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_title_body);
        closeKeyboard();
        selectTitleView(null);
        toggleBody(l, titleToggle);
        toggleOff(l);
    }

    public void toggleCategory(View view) {
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_category_body);
        selectCategoryView(null);
        toggleBody(l, categoryToggle);
        toggleOff(l);
    }

    public void toggleBody(LinearLayout l, ImageView i) {
        if (l.getVisibility() != View.GONE) {
            l.setVisibility(View.GONE);
            i.setImageDrawable(getResources().getDrawable(R.drawable.icon_plus));
        } else {
            l.setVisibility(View.VISIBLE);
            i.setImageDrawable(getResources().getDrawable(R.drawable.icon_minus));
        }
    }

    public void toggleOff(LinearLayout layout) {
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_discover_body);
        LinearLayout k = (LinearLayout) findViewById(R.id.layout_category_body);
        LinearLayout m = (LinearLayout) findViewById(R.id.layout_title_body);
        if (layout != (l)) {
            l.setVisibility(View.GONE);
            discoverToggle.setImageDrawable(getResources().getDrawable(R.drawable.icon_plus));
        }
        if (layout != (k)) {
            k.setVisibility(View.GONE);
            categoryToggle.setImageDrawable(getResources().getDrawable(R.drawable.icon_plus));
        }
        if (layout != (m)) {
            m.setVisibility(View.GONE);
            titleToggle.setImageDrawable(getResources().getDrawable(R.drawable.icon_plus));
        }
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Search.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        if(title.getText().toString().equals("")) {
            displayError("Enter a title.");
        } else {
            intent.putExtra("Title", title.getText().toString());
            startActivity(intent);
        }
    }

    public void categoryQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra("Query", "Category");
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if (rb == null) {
            displayError("Select a category.");
        } else {
            intent.putExtra("Category", categoryMap.get(rb.getText().toString()));
            startActivity(intent);
        }
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

    public void displayError(String error) {
        TextView textError = (TextView) findViewById(R.id.text_error_message);
        textError.setText(error);
    }

    @Override
    public void onItemSelected(AdapterView<?> a, View v,
                               int i, long j) {
        selectDiscoverView(null);
    }

}
