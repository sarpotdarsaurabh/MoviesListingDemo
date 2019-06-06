package com.saurabh.movieslistingdemo.interfaces;

import com.saurabh.movieslistingdemo.models.Movie;

/**
 * Created by saurabhs on 4/6/19.
 */

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);
    void onError();
}
