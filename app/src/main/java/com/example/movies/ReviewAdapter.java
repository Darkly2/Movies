package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

//    private OnReachEndListener onReachEndListener;

    private final static String POSITIVE = "Позитивный";
    private final static String NEUTRAL = "Нейтральный";
    private final static String DEFAULT_TITTLE = "Отзыв ";

//    interface OnReachEndListener {
//        void OnReachEnd();
//    }

//    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
//        this.onReachEndListener = onReachEndListener;
//    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewReviewText;
        private final TextView textViewReviewTittle;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewReviewText = itemView.findViewById(R.id.textViewReviewText);
            textViewReviewTittle = itemView.findViewById(R.id.textViewReviewTittle);
        }
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.review_item,
                        parent,
                        false
                );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ReviewViewHolder holder,
            int position
    ) {
        Review review = reviews.get(position);
        if (review.getTittle() != null) {
            holder.textViewReviewTittle.setText(review.getTittle());
        } else {
            holder.textViewReviewTittle.setText(
                    DEFAULT_TITTLE + String.valueOf(position + 1)
            );
        }
        holder.textViewReviewText.setText(review.getReviewText());
        int colorResId;
        switch (review.getType()) {
            case POSITIVE: {
                colorResId = android.R.color.holo_green_dark;
                break;
            }
            case NEUTRAL: {
                colorResId = android.R.color.holo_orange_dark;
                break;
            }
            default: {
                colorResId = android.R.color.holo_red_dark;
                break;
            }
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.itemView.setBackgroundColor(color);
//        if (position >= reviews.size() - 5 && onReachEndListener != null) {
//            onReachEndListener.OnReachEnd();
//        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
