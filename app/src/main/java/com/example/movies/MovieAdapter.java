package com.example.movies;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private OnEndReachListener onEndReachListener;
    private OnImageClickListener onImageClickListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;

        private final TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPosterDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.movie_item,
                        parent,
                        false
                );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MovieViewHolder holder,
            int position
    ) {
        Movie movie = movies.get(position);
        Glide.with(holder.imageViewPoster)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);
        double rating = Double.parseDouble(movie.getRating().getRatingKp());
        int drawableId;
        if (rating >= 7) {
            drawableId = R.drawable.rating_green_circle;
        } else if (rating >= 5) {
            drawableId = R.drawable.rating_orange_circle;
        } else {
            drawableId = R.drawable.rating_red_circle;
        }
        Drawable drawable = ContextCompat.getDrawable(
                holder.itemView.getContext(),
                drawableId
        );
        holder.textViewRating.setBackground(drawable);
        holder.textViewRating.setText(String.format("%.1f", rating));
        if (position >= movies.size() - 15 && onEndReachListener != null) {
            onEndReachListener.onEndReach();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClickListener != null) {
                    onImageClickListener.onClickImage(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    interface OnEndReachListener {
        void onEndReach();
    }

    interface OnImageClickListener {
        void onClickImage(Movie movie);
    }

    public void setOnEndReachListener(OnEndReachListener onEndReachListener) {
        this.onEndReachListener = onEndReachListener;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

}
