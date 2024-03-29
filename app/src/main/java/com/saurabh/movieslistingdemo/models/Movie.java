package com.saurabh.movieslistingdemo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saurabh.movieslistingdemo.Constants;

import java.util.List;

/**
 * Created by saurabhs on 4/6/19.
 */
@Entity(tableName = Constants.TABLE_NAME_MOVIES)
public class Movie {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "movie_title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "movie_poster_path")
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @ColumnInfo(name = "movie_release_date")
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @ColumnInfo(name = "movie_vote_average")
    @SerializedName("vote_average")
    @Expose
    private float rating;

    @TypeConverters(MovieGenreIdsConverter.class)
    @ColumnInfo(name = "movie_genre_ids")
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;


    @ColumnInfo(name = "movie_overview")
    @SerializedName("overview")
    @Expose
    private String overview;


    @ColumnInfo(name = "movie_backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    private String backdrop;

    @TypeConverters(MovieGenresConverter.class)
    @ColumnInfo(name = "movie_details_genres")
    @SerializedName("genres")
    @Expose
    private List<Genre> genres;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
