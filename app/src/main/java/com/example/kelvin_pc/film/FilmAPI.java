package com.example.kelvin_pc.film;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FilmAPI extends AsyncTask<String, Void, String> {

    private final String genreURL = "https://api.themoviedb.org/3/genre/movie/list?";
    private final String imageURL = "http://image.tmdb.org/t/p/w500/";
    private final String BASE = "https://api.themoviedb.org/3/";
    private final String KEY = "api_key=03689a90e8f749caa858f5a89088998a";
    private final String search = "discover/";
    private final String title = "movie?";
    private final String genres = "with_genres=";
    private final String year = "release_date.gte=";
    private final String vote = "vote_average.gte=";
    private final String a = "&";
    private final String page = "page=";
    private String genreVal;
    private String yearVal;
    private String ratingVal;
    private final int THRESH = 100;
    public AsyncResponse delegate = null;

    public FilmAPI() {

    }

    protected String doInBackground(String... URL) {
        try {
            URL url = new URL(URL[0]);
            Log.d("myTag", url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                int i = 0;
                while ((line = br.readLine()) != null && i < THRESH) {
                    sb.append(line).append("\n");
                    i++;
                }
                br.close();
                return sb.toString();
            } catch (Exception d) {
                logError(d, "doInBackground 2");
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            logError(e, "doInBackground 1");
        }
        return null;
    }

    public void onPostExecute(String result) {
        try {
            JSONObject returnedJSON = new JSONObject(result);
            JSONArray ja = returnedJSON.getJSONArray("results");
            ArrayList<Film> films = new ArrayList<>();
            for (int i=0; i<20; i++) {
                JSONObject jo = ja.getJSONObject(i);
                String poster = imageURL + jo.getString("poster_path").toString();
                String description = jo.get("overview").toString();
                String title = jo.get("original_title").toString();
                String rating = jo.get("vote_average").toString();
                Film film = new Film(title, description, "Action", rating, poster);
//                Log.d("myTag", poster + " " + description + " " + title + " " + rating);
                films.add(film);
            }
            delegate.processFinish(films);
        } catch (Exception e) {
            logError(e, "postExecute");
        }
    }

    public void generateQuery(String genreVal, String yearVal, String ratingVal) {
        this.genreVal = genreVal;
        this.yearVal = yearVal;
        this.ratingVal = ratingVal;
        String x = BASE + search + title + genres + "28" + a + year + yearVal + a + vote + ratingVal + a + KEY;
        this.execute(x);
    }

    public void getGenres() {
        String x = genreURL + KEY;
        this.execute(x);
//        Log.d("myTag", getGenreID("Comedy"));
    }

//    public String getGenreID(String genre) {
//        try {
//            JSONArray jo = genreJSON.getJSONArray("genres");
//            for (int i = 0; i < jo.length(); i++) {
//                try {
//                    JSONObject j = (JSONObject) jo.get(i);
//                    String name = j.get("name").toString();
//                    if (name.equals(genre))
//                        return j.get("id").toString();
//                } catch (Exception e) {
//                    logError(e, "getGenreID 2");
//                }
//            }
//        } catch (Exception e) {
//            logError(e, "getGenreID 1");
//        }
//        return null;
//    }


    public void logError(Exception e, String from) {
        Log.d("myTag", from + ": " + e.toString());
    }

}


