package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMovieActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouriteMovies;
    private MovieAdapter movieAdapter;

    private FavouriteMoviesViewModel viewModel;

    private static final String MOVIE_EXTRA = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movie);
        initViews();
        viewModel = new ViewModelProvider(this)
                .get(FavouriteMoviesViewModel.class);

        movieAdapter = new MovieAdapter();
        recyclerViewFavouriteMovies.setAdapter(movieAdapter);
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(
                this,
                2
        ));

        movieAdapter.setOnImageClickListener(new MovieAdapter.OnImageClickListener() {
            @Override
            public void onClickImage(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(
                        movie,
                        FavouriteMovieActivity.this
                );
                startActivity(intent);
            }
        });

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
    }

    private void initViews() {
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, FavouriteMovieActivity.class);
    }
}