package com.saurabh.movieslistingdemo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.saurabh.movieslistingdemo.interfaces.OnMoviesClickCallback;
import com.saurabh.movieslistingdemo.models.Genre;
import com.saurabh.movieslistingdemo.models.Movie;
import com.saurabh.movieslistingdemo.models.MovieModelForDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhs on 4/6/19.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Genre> allGenres;
    private List<Movie> movies;
    private List<MovieModelForDb>localList;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";
    private OnMoviesClickCallback callback;
    public MoviesAdapter(List<Movie> movies, List<Genre> allGenres,OnMoviesClickCallback callback) {
        this.callback = callback;
        this.movies = movies;
        this.allGenres = allGenres;

    }
    public MoviesAdapter(List<MovieModelForDb> movies,OnMoviesClickCallback callback) {
        this.callback = callback;
        this.localList = movies;

    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (!Constants.isOfflineModeEnabled){
        holder.bind(movies.get(position));
        }else {
            MovieModelForDb movie=localList.get(position);
            holder.releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            holder.title.setText(movie.getTitle());
            holder.rating.setText(String.valueOf(movie.getRating()));
            //holder.genres.setText(getGenres(movie.getGenreIds()));
            Glide.with(holder.itemView)
                    .load(IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(holder.poster);
        }
    }

    @Override
    public int getItemCount() {
        if (!Constants.isOfflineModeEnabled)
        return movies.size();
        else
            return localList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        Movie movie;
        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(movie);
                }
            });
            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            title = itemView.findViewById(R.id.item_movie_title);
            rating = itemView.findViewById(R.id.item_movie_rating);
            genres = itemView.findViewById(R.id.item_movie_genre);
            poster = itemView.findViewById(R.id.item_movie_poster);

        }

        public void bind(Movie movie) {
            this.movie = movie;
            releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            title.setText(movie.getTitle());
            rating.setText(String.valueOf(movie.getRating()));
            genres.setText(getGenres(movie.getGenreIds()));
            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(poster);

        }
        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : allGenres) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }
    }
    public void appendMovies(List<Movie> moviesToAppend,List<MovieModelForDb>localMovies) {
        if (moviesToAppend !=null && movies!=null && !moviesToAppend.isEmpty())
        movies.addAll(moviesToAppend);
        if (localMovies !=null && localList!=null && !localMovies.isEmpty())
        localList.addAll(localMovies);
        notifyDataSetChanged();
    }
    public void clearMovies() {
        if (movies !=null)
        movies.clear();
        if (localList!=null)
        localList.clear();
        notifyDataSetChanged();
    }
}
