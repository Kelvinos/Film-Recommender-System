package com.example.kelvin_pc.film;


import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    private String title, description, genre, rating, userRating, img;

    public Film(String title, String description, String genre, String rating, String img) {
        super();
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.img = img;
    }

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

    public void setUserRating(String rating) { this.userRating = rating; }

    public String getUserRating () { return this.userRating; }

    public Film(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.genre = in.readString();
        this.rating = in.readString();
        this.img = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(genre);
        dest.writeString(rating);
        dest.writeString(img);
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
