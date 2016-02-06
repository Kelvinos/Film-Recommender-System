package com.example.kelvin_pc.film;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Film> {

    private final Context context;
    private final ArrayList<Film> films;

    public ListAdapter(Context context, ArrayList<Film> films) {
        super(context, R.layout.row, films);
        this.context = context;
        this.films = films;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.text_title);
        TextView description = (TextView) rowView.findViewById(R.id.text_description);
        TextView rating = (TextView) rowView.findViewById(R.id.text_rating);
        TextView genre = (TextView) rowView.findViewById(R.id.text_genre);
        title.setText(films.get(position).getTitle());
        description.setText(films.get(position).getDescription());
        genre.setText(films.get(position).getGenre());
        rating.setText(films.get(position).getRating());
        return rowView;
    }


}
