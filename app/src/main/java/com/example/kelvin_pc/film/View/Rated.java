package com.example.kelvin_pc.film.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;
import com.example.kelvin_pc.film.Model.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Rated extends BaseActivity {

    private ListAdapter la;
    private User u;
    private ArrayList<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_rated);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        generateVariables();
        generateRatings();
    }

    public void generateVariables() {
        u = System_Variables.USER;
        la = null;
        HashMap<Film, Integer> ratings = u.getRatings();
        films = new ArrayList<>(ratings.keySet());
        if (System_Variables.ORDER) {
            Collections.reverse(films);
        }
    }

    public void generateRatings() {
        ListView lv = (ListView) findViewById(R.id.list_view_ratings);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(Rated.this, Film_Details.class);
                Bundle b = new Bundle();
                b.putParcelable("film", films.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        la = new ListAdapter(this, films);
        lv.setAdapter(la);
    }

    public void order(View view) {
        System_Variables.ORDER = !System_Variables.ORDER;
        Collections.reverse(films);
        la.notifyDataSetChanged();
    }

    public void clear(View view) {
        User u = System_Variables.USER;
        u.clearRatings();
        la.clear();
    }

    public class ListAdapter extends ArrayAdapter<Film> {

        private final Context context;
        private ArrayList<Film> films;
        private View ratedView;

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
            ratedView = inflater.inflate(R.layout.rated, null);
            generateTitle(position);
            generateBackdrop(position);
            generateRated(position);
            return ratedView;
        }

        public void generateRated(final int position) {
            LinearLayout l = (LinearLayout) ratedView.findViewById(R.id.layout_title);
            User u = System_Variables.USER;
            int rating = u.getRating(films.get(position));
            if (rating == 0) { }
            if (rating == 1) { l.setBackgroundColor(Color.GREEN); }
            if (rating == -1) { l.setBackgroundColor(Color.RED); }
        }

        public void generateTitle(final int position) {
            TextView title = (TextView) ratedView.findViewById(R.id.text_title);
            TextView number = (TextView) ratedView.findViewById(R.id.text_number);
            title.setText(films.get(position).getTitle());
            number.setText(Integer.toString(position + 1));
        }

        public void generateBackdrop(final int position) {
            final ImageView image = (ImageView) ratedView.findViewById(R.id.image_poster);
            try {
                new Image_Downloader(image).execute(films.get(position).getBackdrop());
            } catch (Exception e) {
                new Debugger().print(e.toString());
            }
        }

    }

}
