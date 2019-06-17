package com.saurabh.movieslistingdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.saurabh.movieslistingdemo.interfaces.OnGetGenresCallback;
import com.saurabh.movieslistingdemo.interfaces.OnGetMoviesCallback;
import com.saurabh.movieslistingdemo.interfaces.OnMoviesClickCallback;
import com.saurabh.movieslistingdemo.models.Genre;
import com.saurabh.movieslistingdemo.models.Movie;
import com.saurabh.movieslistingdemo.repository.MovieDatabase;
import com.saurabh.movieslistingdemo.repository.MoviesRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;

    private MoviesRepository moviesRepository;
    private List<Genre> movieGenres;

    private boolean isFetchingMovies;
    private int currentPage = 1;
    private String sortBy = MoviesRepository.POPULAR;

    private MovieDatabase movieDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        moviesRepository = MoviesRepository.getInstance();
        movieDatabase = MovieDatabase.getInstance(this);
        moviesList = findViewById(R.id.movies_list);
        setupOnScrollListener();
        getGenres();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                showSortMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortMenu() {
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.sort));
        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                currentPage = 1;

                switch (item.getItemId()) {
                    case R.id.popular:
                        sortBy = MoviesRepository.POPULAR;
                        getMovies(currentPage);
                        return true;
                    case R.id.top_rated:
                        sortBy = MoviesRepository.TOP_RATED;
                        getMovies(currentPage);
                        return true;
                    case R.id.upcoming:
                        sortBy = MoviesRepository.UPCOMING;
                        getMovies(currentPage);
                        return true;
                    case R.id.offlineMode:
                        showOfflineDialog();
                        return true;
                    default:
                        return false;
                }
            }
        });
        sortMenu.inflate(R.menu.menu_movies_sort);
        sortMenu.show();
    }

    private void showOfflineDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Offline Mode");
        if (Constants.isOfflineModeEnabled) {
            b.setMessage("Do you want to disable offline mode? This will delete the local data and fetch data from server again.");
        } else {
            b.setMessage("Do you want to enable offline mode? This will show the list of movie data stored locally on this device.");
        }
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (Constants.isOfflineModeEnabled) {
                    Constants.isOfflineModeEnabled=false;
                    getGenres();
                } else {
                    Constants.isOfflineModeEnabled=true;
                    if (adapter == null) {
                        adapter = new MoviesAdapter(movieDatabase.getMoviesDao().getAllMovies(),movieDatabase.getGenresDao().getAllMovies(), callback);
                        moviesList.setAdapter(adapter);
                    } else {
                     adapter.clearMovies();
                        adapter.appendMovies(movieDatabase.getMoviesDao().getAllMovies());
                    }
                }

            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog a=b.create();
        a.show();
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(manager);
        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies && currentPage<5) {
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                Log.e("GenresResp","Genres are->\n"+new Gson().toJson(genres));
                movieGenres = genres;
                for (Genre g:movieGenres){
                movieDatabase.getGenresDao().insertGenre(g);
                }
                getMovies(currentPage);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getMovies(int page) {
        Constants.showProgressDialog(this,true,"Fetching movies.. Please wait");
        isFetchingMovies = true;
        moviesRepository.getMovies(page, sortBy, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                Constants.showProgressDialog(MainActivity.this,false,"Fetching movies.. Please wait");
                Log.d("MoviesRepository", "Current Page = " + page);
                Log.e("MoviesResp","Movies are->\n"+new Gson().toJson(movies));
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres, callback);
                    moviesList.setAdapter(adapter);
                } else {
                    if (page == 1) {
                        adapter.clearMovies();
                    }
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;
                setTitle();
                for (Movie m:movies)
                    movieDatabase.getMoviesDao().insertMovie(m);

            }

            @Override
            public void onError() {
                Constants.showProgressDialog(MainActivity.this,false,"Fetching movies.. Please wait");

            }
        });
    }

    private void setTitle() {
        switch (sortBy) {
            case MoviesRepository.POPULAR:
                setTitle(getString(R.string.popular));
                break;
            case MoviesRepository.TOP_RATED:
                setTitle(getString(R.string.top_rated));
                break;
            case MoviesRepository.UPCOMING:
                setTitle(getString(R.string.upcoming));
                break;
        }
    }

    private void showError() {
        Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }

    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
        @Override
        public void onClick(Movie movie) {
            if (true){//!Constants.isOfflineModeEnabled){
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            intent.putExtra(MovieActivity.MOVIE_ID, movie.getId());
            startActivity(intent);
        }
        }
    };
}
