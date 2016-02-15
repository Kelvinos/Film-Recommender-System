package com.example.kelvin_pc.film.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    private String title, tag, description, genre, rating, userRating, img, runTime, releaseDate;

    public Film(String title, String tag, String description, String genre, String rating, String img, String runTime, String releaseDate) {
        super();
        this.title = title;
        this.tag = tag;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.img = img;
        this.runTime = runTime;
        this.releaseDate = releaseDate;
    }

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
    public String getUserRating () { return this.userRating; }
    public String getTag() { return this.tag; }

    public void setUserRating(String rating) { this.userRating = rating; }

    public Film(Parcel in) {
        this.title = in.readString();
        this.tag = in.readString();
        this.description = in.readString();
        this.genre = in.readString();
        this.rating = in.readString();
        this.img = in.readString();
        this.runTime = in.readString();
        this.releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(tag);
        dest.writeString(description);
        dest.writeString(genre);
        dest.writeString(rating);
        dest.writeString(img);
        dest.writeString(runTime);
        dest.writeString(releaseDate);
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
}
