package com.saurabh.movieslistingdemo.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saurabh.movieslistingdemo.Constants;
import com.saurabh.movieslistingdemo.models.Genre;
import com.saurabh.movieslistingdemo.models.Movie;

import java.util.List;

/**
 * Created by saurabhs on 14/6/19.
 */
@Dao
public interface GenresDao {
    @Query("SELECT * FROM "+ Constants.TABLE_NAME_GENRES)
    List<Genre> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenre(Genre movie);

    @Update
    void updateGenre(Genre repos);


    @Delete
    void deleteGenre(Genre note);


    @Delete
    void deleteGenre(Genre... note);
}
