package com.saurabh.movieslistingdemo.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saurabh.movieslistingdemo.Constants;
import com.saurabh.movieslistingdemo.models.Movie;
import com.saurabh.movieslistingdemo.models.MovieModelForDb;

import java.util.List;

/**
 * Created by saurabhs on 12/6/19.
 */
@Dao
public interface MoviesDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_MOVIES)
    List<MovieModelForDb>getAllMovies();

    @Insert
    void insertMovie(MovieModelForDb movie);

    @Update
    void updateNote(MovieModelForDb repos);


    @Delete
    void deleteNote(MovieModelForDb note);


    @Delete
    void deleteNotes(MovieModelForDb... note);


}
