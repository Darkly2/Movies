package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {

    private MoviesDao moviesDao;
    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDao = MovieDataBase.getInstance(application).moviesDao();
        getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesDao.getAllFavouriteMovies();
    }
}
