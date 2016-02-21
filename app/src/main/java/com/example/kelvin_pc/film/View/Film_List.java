package com.example.kelvin_pc.film.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.AsyncResponse;
import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Film_Downloader;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.BaseActivity;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Film_List extends BaseActivity implements AsyncResponse {

    private String genre, release, releaseSort, rating, ratingSort, votes, votesSort, title, category, query;
    private Spinner sort, order;
    private HashMap<String, String> sortByMap;
    private ProgressDialog progress;
    private ListAdapter la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_filmlist);
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (la != null) {
            if (System_Variables.USER.getUpdated()) {
                la.notifyDataSetChanged();
                System_Variables.USER.setUpdated(false);
            }
        }
    }

    public void init() {
        generateVariables();
        initSpinners();
        getBundleData();
        generateFilmQuery(query);
    }

    public void generateVariables() {
        System_Variables.PAGE_NUMBER = 1;
        sortByMap = new HashMap<>();
        sortByMap.put("Popularity", "popularity");
        sortByMap.put("Release Date", "release_date");
        sortByMap.put("Revenue", "revenue");
        sortByMap.put("Title", "original_title");
        sortByMap.put("Rating", "vote_average");
        sortByMap.put("Votes", "vote_count");
    }

    public void initSpinners() {
        sort = makeSpinner(R.id.spinner_sort_by, R.array.sort_by_array, 0);
        order = makeSpinner(R.id.spinner_order_by, R.array.order_by_array, 0);
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

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        query = b.getString("Query");
        switch (query) {
            case "Title":
                getBundleTitleData(b);
                break;
            case "Category":
                getBundleCategoryData(b);
                break;
            case "Discover":
                getBundleDiscoverData(b);
                break;
            default:
                break;
        }
    }

    public void getBundleTitleData(Bundle b) {
        title = b.getString("Title");
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_sort);
        l.setVisibility(LinearLayout.INVISIBLE);
    }

    public void getBundleCategoryData(Bundle b) {
        category = b.getString("Category");
        LinearLayout l = (LinearLayout) findViewById(R.id.layout_sort);
        l.setVisibility(LinearLayout.INVISIBLE);
    }

    public void getBundleDiscoverData(Bundle b) {
        genre = b.getString(getString(R.string.genre));
        release = b.getString(getString(R.string.release_date));
        releaseSort = b.getString(getString(R.string.release_date) + "x");
        rating = b.getString(getString(R.string.rating));
        ratingSort = b.getString(getString(R.string.rating) + "x");
        votes = b.getString(getString(R.string.vote_count));
        votesSort = b.getString(getString(R.string.vote_count) + "x");
    }

    public void generateFilmQuery(String query) {
        updatePageText();
        updateButtons();
        Film_Downloader fa = new Film_Downloader();
        fa.delegate = this;

        String page = Integer.toString(System_Variables.PAGE_NUMBER);
        if (query.equals("Title")) {
            fa.generateTitleQuery(title, page);
        } else if (query.equals("Category")) {
            fa.generateCategoryQuery(category, page);
        } else if (query.equals("Discover")) {
            String sortBy = getSortByString(sort.getSelectedItem().toString());
            String orderBy = order.getSelectedItem().toString();
            fa.generateDiscoverQuery(genre, release, releaseSort, rating, ratingSort, votes, votesSort, sortBy, orderBy, page);
        }
        startProgressBar();
    }

    @Override
    public void processFinish(final ArrayList<Film> output) {
        stopProgressBar();
        if (output != null) {
            generateFilmList(output);
        } else {
            displayNoResults();
        }
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
        generateFilmQuery(query);
    }

    public void previousPage(View view) {
        if (System_Variables.PAGE_NUMBER != 1)
            System_Variables.PAGE_NUMBER = System_Variables.PAGE_NUMBER - 1;
        generateFilmQuery(query);
    }

    public void refresh(View view) {
        generateFilmQuery(query);
    }

    public void displayNoResults() {
        AlertDialog alertDialog = new AlertDialog.Builder(Film_List.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("No results found.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }

    public String getSortByString(String s) {
        return sortByMap.get(s);
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
            generateColor(position);
            generateTitle(position);
            generateTag(position);
            generateImage(position);
            return rowView;
        }

        public void generateColor(final int position) {
            LinearLayout l = (LinearLayout) rowView.findViewById(R.id.layout_row);
            User u = System_Variables.USER;
            int rating = u.getRating(films.get(position));
            if (rating == 0) { }
            if (rating == 1) { l.setBackgroundColor(Color.GREEN); }
            if (rating == -1) { l.setBackgroundColor(Color.RED); }
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
