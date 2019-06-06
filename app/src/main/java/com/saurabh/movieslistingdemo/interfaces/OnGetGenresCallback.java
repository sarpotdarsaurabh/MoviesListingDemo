package com.saurabh.movieslistingdemo.interfaces;

import com.saurabh.movieslistingdemo.models.Genre;

import java.util.List;

/**
 * Created by saurabhs on 4/6/19.
 */

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}
