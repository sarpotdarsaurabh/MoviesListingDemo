package com.saurabh.movieslistingdemo.models;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by saurabhs on 14/6/19.
 */

public class MovieGenresConverter implements Serializable {
    @TypeConverter // note this annotation
    public String fromGenresList(List<Genre> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Genre>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Genre> toGenresList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Genre>>() {
        }.getType();
        List<Genre> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
