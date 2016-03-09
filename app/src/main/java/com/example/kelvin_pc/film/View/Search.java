package com.example.kelvin_pc.film.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kelvin_pc.film.Model.System.String_Mapping;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

public class Search extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Spinner genre, releaseS, releaseE, ratingS, ratingE, voteCountS, voteCountE;
    private LinearLayout titleLayout, categoryLayout, discoverLayout, titleTitleLayout, categoryTitleLayout, discoverTitleLayout,
            discoverBody, categoryBody, titleBody;
    private RadioGroup rg;
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
        if (System_Variables.USER == null)
            System_Variables.USER = new User();
        if (System_Variables.STRING_MAPPING == null)
            System_Variables.STRING_MAPPING = new String_Mapping();
    }

    public void initTitleSection() {
        title = editText(R.id.text_title);
        titleBody = linearLayout(R.id.layout_title_body);
        titleLayout = linearLayout(R.id.layout_title);
        titleTitleLayout = linearLayout(R.id.layout_title_title);
        delete = imageButton(R.id.image_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title.setText(null);
                delete.setVisibility(View.GONE);
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
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
        rg = (RadioGroup) findViewById(R.id.radio_group);
        categoryBody = linearLayout(R.id.layout_category_body);
        categoryLayout = linearLayout(R.id.layout_category);
        categoryTitleLayout = linearLayout(R.id.layout_category_title);
    }

    public void initDiscoverSection() {
        discoverBody = linearLayout(R.id.layout_discover_body);
        discoverLayout = linearLayout(R.id.layout_discover);
        discoverTitleLayout = linearLayout(R.id.layout_discover_title);
        genre = spinner(R.id.spinner_genre, R.array.genre_array, 0);
        releaseS = spinner(R.id.spinner_year_start, R.array.release_date_array, 26);
        releaseE = spinner(R.id.spinner_year_end, R.array.greater_lesser_array, 0);
        ratingS = spinner(R.id.spinner_rating_start, R.array.rating_array, 7);
        ratingE = spinner(R.id.spinner_rating_end, R.array.greater_lesser_array, 0);
        voteCountS = spinner(R.id.spinner_runtime_start, R.array.vote_count_array, 0);
        voteCountE = spinner(R.id.spinner_runtime_end, R.array.greater_lesser_array, 0);
    }

    public void initView() {
        toggleOff();
        toggleDiscover(null);
        delete.setVisibility(View.GONE);
    }

    public void toggleDiscover(View view) {
        toggleOn("Discover", discoverBody, discoverTitleLayout, discoverLayout);
    }

    public void toggleTitle(View view) {
        toggleOn("Title", titleBody, titleTitleLayout, titleLayout);
        title.setCursorVisible(true);
    }

    public void toggleCategory(View view) {
        toggleOn("Category", categoryBody, categoryTitleLayout, categoryLayout);
    }

    public void toggleOn(String sel, LinearLayout body, LinearLayout title, LinearLayout layout) {
        System_Variables.QUERY = sel;
        toggleOff();
        body.setVisibility(View.VISIBLE);
        setColorTheme(title, layout);
    }

    public void toggleOff() {
        discoverBody.setVisibility(View.GONE);
        titleBody.setVisibility(View.GONE);
        categoryBody.setVisibility(View.GONE);
        unColourTheme(discoverTitleLayout, discoverLayout);
        unColourTheme(titleTitleLayout, titleLayout);
        unColourTheme(categoryTitleLayout, categoryLayout);
        closeKeyboard();
        displayError("");
    }

    public void setColorTheme(LinearLayout title, LinearLayout layout) {
        title.setBackgroundResource(R.color.darker_grey);
        layout.setBackgroundResource(R.color.light_grey);
    }

    public void unColourTheme(LinearLayout title, LinearLayout layout) {
        title.setBackgroundColor(Color.TRANSPARENT);
        layout.setBackgroundColor(Color.TRANSPARENT);
    }

    public void SearchFilms(View view) {
        if (findError())
            return;
        System_Variables.SEARCH_REQUEST = true;
        if (System_Variables.QUERY.equals("Discover"))
            discoverQuery();
        else if (System_Variables.QUERY.equals("Title"))
            titleQuery();
        else if (System_Variables.QUERY.equals("Category"))
            categoryQuery();
    }

    public void titleQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra("Title", title.getText().toString());
        startActivity(intent);
    }

    public void categoryQuery() {
        Intent intent = new Intent(this, Film_List.class);
        RadioButton rb = radioButton(rg.getCheckedRadioButtonId());
        intent.putExtra("Category", System_Variables.STRING_MAPPING.categoryMap.get(rb.getText().toString()));
        startActivity(intent);
    }

    public void discoverQuery() {
        Intent intent = new Intent(this, Film_List.class);
        intent.putExtra(getString(R.string.genre), genre.getSelectedItem().toString());
        intent.putExtra(getString(R.string.release_date), releaseS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.release_date) + "x", System_Variables.STRING_MAPPING.logicMap.get((releaseE.getSelectedItem().toString())));
        intent.putExtra(getString(R.string.rating), ratingS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.rating) + "x", System_Variables.STRING_MAPPING.logicMap.get(ratingE.getSelectedItem().toString()));
        intent.putExtra(getString(R.string.vote_count), voteCountS.getSelectedItem().toString());
        intent.putExtra(getString(R.string.vote_count) + "x", System_Variables.STRING_MAPPING.logicMap.get(voteCountE.getSelectedItem().toString()));
        startActivity(intent);
    }

    public boolean findError() {
        RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if (title.getText().toString().equals("") && System_Variables.QUERY.equals("Title")) {
            displayError("Enter a title.");
            return true;
        }
        else if (rb == null && System_Variables.QUERY.equals("Category")) {
            displayError("Select a category.");
            return true;
        }
        return false;
    }

    public void displayError(String error) {
        TextView textError = textView(R.id.text_error_message);
        textError.setText(error);
    }

}
