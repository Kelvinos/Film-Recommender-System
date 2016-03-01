package com.example.kelvin_pc.film.Model.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kelvin_pc.film.Controller.Debugger;
import com.example.kelvin_pc.film.Controller.Image_Downloader;
import com.example.kelvin_pc.film.Model.Film;
import com.example.kelvin_pc.film.Model.System.System_Variables;
import com.example.kelvin_pc.film.Model.User;
import com.example.kelvin_pc.film.R;

import java.util.ArrayList;

public class Recommend_Adapter extends ArrayAdapter<Film> {

    private final Context context;
    private ArrayList<Film> films;
    private View rowView;

    public Recommend_Adapter(Context context, ArrayList<Film> films) {
        super(context, R.layout.row, films);
        this.context = context;
        this.films = films;
    }

    public void addItems(ArrayList<Film> films) {
        this.films = films;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (films != null) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row, null);
            try {
                generateColor(position);
                generateTitle(position);
                generateTag(position);
                generateImage(position);
            } catch (Exception e) {
                new Debugger().print(e.toString());
            }
        } else {
            new Debugger().print("empty");
        }
        return rowView;
    }

    public void generateColor(final int position) {
        LinearLayout l = (LinearLayout) rowView.findViewById(R.id.layout_row);
        User u = System_Variables.USER;

        int rating = u.getRating(films.get(position));
        if (rating == 0) {
        }
        if (rating == 1) {
            l.setBackgroundColor(Color.GREEN);
        }
        if (rating == -1) {
            l.setBackgroundColor(Color.RED);
        }

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


