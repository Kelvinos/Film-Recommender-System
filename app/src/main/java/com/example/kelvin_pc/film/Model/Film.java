package com.example.kelvin_pc.film.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Film implements Parcelable, Serializable, Comparable {

    private String title, tag, description, genre, rating, img, runTime, releaseDate, backdrop;
    private int id;

    public Film(int id, String title, String tag, String description, String genre, String rating, String img, String runTime, String releaseDate, String backdrop) {
        super();
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.img = img;
        this.runTime = runTime;
        this.releaseDate = releaseDate;
        this.backdrop = backdrop;
    }

    public int getId() { return this.id; }
    public String getRunTime() { return this.runTime; }
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public String getRating() {
        return this.rating;
    }
    public String getGenre() {
        return this.genre;
    }
    public String getImg() { return  this.img; }
    public String getReleaseDate() { return this.releaseDate; }
    public String getTag() { return this.tag; }
    public String getBackdrop() { return this.backdrop; }

    public Film(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.tag = in.readString();
        this.description = in.readString();
        this.genre = in.readString();
        this.rating = in.readString();
        this.img = in.readString();
        this.runTime = in.readString();
        this.releaseDate = in.readString();
        this.backdrop = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(tag);
        dest.writeString(description);
        dest.writeString(genre);
        dest.writeString(rating);
        dest.writeString(img);
        dest.writeString(runTime);
        dest.writeString(releaseDate);
        dest.writeString(backdrop);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        Film f = (Film) o;
        return this.title.compareTo(f.getTitle());
    }
}
