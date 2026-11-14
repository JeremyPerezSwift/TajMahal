package com.openclassrooms.tajmahal.ui.restaurant;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.databinding.ItemReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter responsible for displaying restaurant reviews in a RecyclerView.
 * <p>
 * This class manages a list of {@link Review} objects and uses
 * {@link ItemReviewBinding} to display each review's name, rating,
 * comment and profile picture.
 */
public class ReviewAdapter
        extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    /**
     * Updates the list of reviews and notifies the RecyclerView
     * that its data has changed.
     *
     * @param newReviews The new list of reviews to display.
     */
    public void setReviews(List<Review> newReviews) {
        reviews = new ArrayList<>(newReviews);
        notifyDataSetChanged();
    }

    /**
     * Creates a new ViewHolder using the item_review layout.
     *
     * @param parent The ViewGroup that will contain the new item view.
     * @param viewType The type of view to create.
     * @return A new ReviewViewHolder containing the review layout.
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new ReviewViewHolder(binding);
    }

    /**
     * Binds a review from the list to the provided ViewHolder.
     *
     * @param holder The ViewHolder used to display the review.
     * @param position The position of the review in the list.
     */
    @Override
    public void onBindViewHolder(
            @NonNull ReviewViewHolder holder,
            int position
    ) {
        holder.bind(reviews.get(position));
    }

    /**
     * Returns the total number of reviews in the list.
     *
     * @return The number of reviews.
     */
    @Override
    public int getItemCount() {
        return reviews.size();
    }

    /**
     * ViewHolder responsible for holding the views of a single review.
     * <p>
     * This class uses {@link ItemReviewBinding} to access the UI
     * components and display the review data.
     */
    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ItemReviewBinding binding;

        /**
         * Creates a ViewHolder with the provided view binding.
         *
         * @param binding The binding containing the review's UI components.
         */
        public ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Displays the review's name, comment, rating and profile picture
         * in the corresponding UI components.
         *
         * @param review The review containing the data to display.
         */
        public void bind(Review review) {
            binding.tvReviewName.setText(review.getUsername());
            binding.tvReviewComment.setText(review.getComment());
            binding.ratingBarItem.setRating(review.getRate());

            Glide.with(binding.imageReviewAvatar)
                    .load(review.getPicture())
                    .circleCrop()
                    .into(binding.imageReviewAvatar);
        }
    }
}

