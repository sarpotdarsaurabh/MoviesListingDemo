package com.saurabh.movieslistingdemo.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.saurabh.movieslistingdemo.Constants;
import com.saurabh.movieslistingdemo.interfaces.GenresDao;
import com.saurabh.movieslistingdemo.interfaces.MoviesDao;
import com.saurabh.movieslistingdemo.models.Genre;
import com.saurabh.movieslistingdemo.models.Movie;

/**
 * Created by saurabhs on 12/6/19.
 */
@Database(entities = { Movie.class, Genre.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MoviesDao getMoviesDao();
    public abstract GenresDao getGenresDao();
    private static MovieDatabase movieDatabase;

    public static MovieDatabase getInstance(Context context) {
        if (null == movieDatabase) {
            movieDatabase = buildDatabaseInstance(context);
        }
        return movieDatabase;
    }

    private static MovieDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MovieDatabase.class,
                Constants.DB_NAME)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        movieDatabase = null;
    }
}
