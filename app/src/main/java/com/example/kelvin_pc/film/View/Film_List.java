package com.example.kelvin_pc.film.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Model.Adapters.Film_List_Adapter;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;

public class Film_List extends BaseActivity implements AsyncResponse {

    private String genre, release, releaseSort, rating, ratingSort, votes, votesSort, title, category, query;
    private Spinner sort, order;
    private ProgressDialog progress;
    private Film_List_Adapter la;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_film_list);
        super.onCreate(savedInstanceState);
        if (System_Variables.SEARCH_REQUEST) {
            init();
        } else {
            showAlert("No Results.", "Try searching for a film!", this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (System_Variables.SEARCH_REQUEST)
            init();
        if (la != null)
            la.notifyDataSetChanged();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null)
            setIntent(intent);
    }

    public void init() {
        generateVariables();
        getBundleData();
        generateFilmQuery();
    }

    public void generateVariables() {
        System_Variables.PAGE_NUMBER = 1;
        lv = listView(R.id.listView);
        progress = new ProgressDialog(this);
        sort = spinner(R.id.spinner_sort_by, R.array.sort_by_array, 0);
        order = spinner(R.id.spinner_order_by, R.array.order_by_array, 0);
    }

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        query = System_Variables.QUERY;
        if (query.equals("Discover")) {
            genre = b.getString(getString(R.string.genre));
            release = b.getString(getString(R.string.release_date));
            releaseSort = b.getString(getString(R.string.release_date) + "x");
            rating = b.getString(getString(R.string.rating));
            ratingSort = b.getString(getString(R.string.rating) + "x");
            votes = b.getString(getString(R.string.vote_count));
            votesSort = b.getString(getString(R.string.vote_count) + "x");
        } else if (query.equals("Title")) {
            title = b.getString("Title");
            removeSortBar();
        } else if (query.equals("Category")) {
            category = b.getString("Category");
            removeSortBar();
        }
    }

    public void generateFilmQuery() {
        System_Variables.SEARCH_REQUEST = false;
        Film_Downloader fa = new Film_Downloader();
        fa.delegate = this;
        String page = Integer.toString(System_Variables.PAGE_NUMBER);
        if (query.equals("Discover")) {
            String sortBy = System_Variables.STRING_MAPPING.sortMap.get(sort.getSelectedItem().toString());
            String orderBy = order.getSelectedItem().toString();
            fa.generateDiscoverQuery(genre, release, releaseSort, rating, ratingSort, votes, votesSort, sortBy, orderBy, page);
        } else if (query.equals("Title")) {
            fa.generateTitleQuery(title, page);
            removeSortBar();
        } else if (query.equals("Category")) {
            fa.generateCategoryQuery(category, page);
            removeSortBar();
        }
        startProgressBar(progress, "Loading...");
    }

    @Override
    public void processFinish(final ArrayList<Film> output) {
        if (output.size() != 0) {
            generateFilmList(output);
            update();
        }
        else {
            showAlert("No results.", "Try changing the search criteria!", this);
        }
        stopProgressBar(progress);
    }

    public void generateFilmList(final ArrayList<Film> films) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(Film_List.this, Film_Details.class);
                Bundle b = new Bundle();
                b.putParcelable("film", films.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        if (la == null) {
            la = new Film_List_Adapter(this, films);
            lv.setAdapter(la);
        } else {
            la.addItems(new ArrayList<Film>());
            la.notifyDataSetChanged();
            la.addItems(films);
            la.notifyDataSetChanged();
        }
    }

    public void update() {
        updateButtons();
        updatePageText();
        lv.smoothScrollToPosition(0);
    }

    public void updateButtons() {
        Button b = (Button) findViewById(R.id.button_previous);
        Button k = (Button) findViewById(R.id.button_next);
        if (System_Variables.PAGE_NUMBER == 1) {
            b.setClickable(false);
            b.setVisibility(View.INVISIBLE);
        } else {
            b.setClickable(true);
            b.setVisibility(View.VISIBLE);
        }
        if (System_Variables.PAGE_NUMBER == System_Variables.PAGE_THRESH) {
            k.setClickable(false);
            k.setVisibility(View.INVISIBLE);
        } else {
            k.setClickable(true);
            k.setVisibility(View.VISIBLE);
        }
    }

    public void updatePageText() {
        TextView tv = textView(R.id.text_page);
        tv.setText("Page " + System_Variables.PAGE_NUMBER + "/" + System_Variables.PAGE_THRESH);
    }

    public void nextPage(View view) {
        if (System_Variables.PAGE_NUMBER != System_Variables.PAGE_THRESH)
            System_Variables.PAGE_NUMBER = System_Variables.PAGE_NUMBER + 1;
        generateFilmQuery();
        update();
    }

    public void previousPage(View view) {
        if (System_Variables.PAGE_NUMBER != 1)
            System_Variables.PAGE_NUMBER = System_Variables.PAGE_NUMBER - 1;
        generateFilmQuery();
        update();
    }

    public void refresh(View view) {
        generateFilmQuery();
    }

    public void removeSortBar() {
        LinearLayout l = linearLayout(R.id.layout_sort);
        l.setVisibility(LinearLayout.GONE);
    }

}
