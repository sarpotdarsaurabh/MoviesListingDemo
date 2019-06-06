package com.saurabh.movieslistingdemo.interfaces;

import com.saurabh.movieslistingdemo.models.Movie;

import java.util.List;

/**
 * Created by saurabhs on 4/6/19.
 */

public interface OnGetMoviesCallback {
    void onSuccess(int page, List<Movie> movies);
    void onError();
}
