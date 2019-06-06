package com.saurabh.movieslistingdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by saurabhs on 4/6/19.
 */

public class GenresResponse {
    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
