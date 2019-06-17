package com.saurabh.movieslistingdemo.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saurabh.movieslistingdemo.Constants;
import com.saurabh.movieslistingdemo.models.Movie;
import java.util.List;

/**
 * Created by saurabhs on 12/6/19.
 */
@Dao
public interface MoviesDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_MOVIES+" ORDER BY movie_id ASC")
    List<Movie>getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie repos);


    @Delete
    void deleteMovie(Movie note);


    @Delete
    void deleteMovie(Movie... note);

    @Query("SELECT * FROM "+ Constants.TABLE_NAME_MOVIES+" WHERE movie_id= :movieId")
    Movie getSelectedMovie(int movieId);
}
