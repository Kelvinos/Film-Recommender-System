package com.example.kelvin_pc.film.Controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kelvin_pc.film.Model.Film;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Film_Downloader extends AsyncTask<String, Void, String> implements AsyncResponse {

    private final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    private final String MOVIE_URL = "https://api.themoviedb.org/3/movie/";
    private final String KEY = "api_key=03689a90e8f749caa858f5a89088998a";
    private final String GENRE = "with_genres=";
    private final String RELEASE_DATE = "release_date";
    private final String RATING = "vote_average";
    private final String VOTE_COUNT = "vote_count";
    private final String SORT_BY = "sort_by=";

    private String PAGE = "page=1";
    private final int THRESH = 100;
    private movieDetails md = new movieDetails();
    public AsyncResponse delegate = null;
    private HashMap<String, String> genreMap;
    private static String pageLimit;

    public Film_Downloader() {
        initGenres();
    }

    protected String doInBackground(String... URL) {
        try {
            URL url = new URL(URL[0]);
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
                new Debugger().print("DO IN BACKGROUND 1", d.toString());
            } finally {
                con.disconnect();
            }
        } catch (Exception e) {
            new Debugger().print("DO IN BACKGROUND 2", e.toString());
        }
        return null;
    }

    public void onPostExecute(String result) {
        try {
            JSONObject returnedJSON = new JSONObject(result);
            String pages = returnedJSON.get("total_pages").toString();
            pageLimit = pages;
            JSONArray ja = returnedJSON.getJSONArray("results");
            ArrayList<String> ids = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                JSONObject jo = ja.getJSONObject(i);
                ids.add(jo.get("id").toString());
            }
            md.delegate = this;
            md.execute(ids);
        } catch (Exception e) {
            delegate.processFinish(null);
            new Debugger().print("ON POST EXECUTE", e.toString());
        }
    }

    @Override
    public void processFinish(ArrayList<Film> films) {
        delegate.processFinish(films);
    }

    public class movieDetails extends AsyncTask<ArrayList<String>, Void, ArrayList<Film>> {

        public AsyncResponse delegate = null;

        public movieDetails() {
        }

        @Override
        protected ArrayList<Film> doInBackground(ArrayList<String>... ids) {
            try {
                ArrayList<Film> films = new ArrayList<>();
                for (int i = 0; i < ids[0].size(); i++) {
                    URL url = new URL(MOVIE_URL + ids[0].get(i) + "&?" + KEY);
                    String js = generateJSON(url);
                    Film f = generateFilmDetails(js);
                    films.add(f);
                }
                return films;
            } catch (Exception e) {
                new Debugger().print("DO IN BACKGROUND", e.toString());
            }
            return null;
        }

        public String generateJSON(URL url) {
            //Log.d("myTag", url.toString());
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    int j = 0;
                    while ((line = br.readLine()) != null && j < THRESH) {
                        sb.append(line).append("\n");
                        j++;
                    }
                    br.close();
                    return sb.toString();
                } catch (Exception d) {
                    new Debugger().print("GENERATE JSON 1", d.toString());
                    return null;
                } finally {
                    con.disconnect();
                }
            } catch (Exception e) {
                new Debugger().print("GENERATE JSON 2", e.toString());
                return null;
            }
        }

        public Film generateFilmDetails(String s) {
            try {
                JSONObject jo = new JSONObject(s);
                String poster = IMAGE_URL + jo.getString("poster_path").toString();
                String backdrop = IMAGE_URL + jo.getString("backdrop_path").toString();
                String description = jo.get("overview").toString();
                String title = jo.get("original_title").toString();
                String rating = jo.get("vote_average").toString();
                String runtime = jo.get("runtime").toString();
                String release = jo.get("release_date").toString();
                String tag = jo.get("tagline").toString();
                int id = Integer.parseInt(jo.get("id").toString());
                JSONArray ja = jo.getJSONArray("genres");
                String genres = "";
                for (int i=0; i<ja.length(); i++) {
                    if (i != ja.length()-1)
                        genres = genres + ja.getJSONObject(i).get("name").toString() + ", ";
                    else
                        genres = genres + ja.getJSONObject(i).get("name").toString();
                }
                Film film = new Film(id, title, tag, description, genres, rating, poster, runtime, release, backdrop);
                return film;
            } catch (Exception e) {
                new Debugger().print("GENERATE FILM DETAILS", e.toString());
                return null;
            }
        }

        public void onPostExecute(ArrayList<Film> films) {
            delegate.processFinish(films);
        }

    }

    public void generateQuery(String genreVal,
                              String yearVal, String yearValSort,
                              String ratingVal, String ratingValSort,
                              String votesVal, String votesValSort,
                              String sortBy, String orderBy, String page) {
        this.PAGE = "page=" + page;
        String x = BASE_URL +
                GENRE + getGenreId(genreVal) + "&" +
                RELEASE_DATE + yearValSort + "=" + yearVal + "&" +
                RATING + ratingValSort + "=" + ratingVal + "&" +
                VOTE_COUNT + votesValSort + "=" + votesVal + "&" +
                SORT_BY + sortBy + "." + orderBy + "&" +
                PAGE + "&" +
                KEY;
        Log.d("myTag", x);
        this.execute(x);
    }

    public void initGenres() {
        genreMap = new HashMap<>();
        String[] genres = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama",
                "Family", "Fantasy", "Foreign", "History", "Horror", "Music", "Mystery", "Romance", "Sci-Fi",
                "TV Movie", "Thriller", "War", "Western"};
        String[] id = {"28", "12", "16", "35", "80", "99", "18", "10751", "14", "10769", "36", "27", "10402",
                "9648", "10749", "878", "10770", "53", "10752", "37"};
        for (int i = 0; i < genres.length; i++) {
            genreMap.put(genres[i], id[i]);
        }
    }

    public String getGenreId(String genre) {
        try {
            return genreMap.get(genre);
        } catch (Exception e) {
            logError(e, "getGenreId");
            return "28";
        }
    }

    public void logError(Exception e, String from) {
        Log.d("myTag", from + ": " + e.toString());
    }


}


