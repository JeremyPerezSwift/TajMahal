package com.openclassrooms.tajmahal.ui.restaurant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ReviewsFragment displays the restaurant reviews and allows users to
 * submit a new review with a rating and a comment.
 * <p>
 * This class uses {@link FragmentReviewsBinding} to access its layout,
 * {@link ReviewsViewModel} to manage review data, and {@link ReviewAdapter}
 * to display existing reviews in a RecyclerView.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;
    private ReviewsViewModel reviewsViewModel;

    /**
     * This method is called when the fragment is first created.
     * It's used to perform one-time initialization.
     *
     * @param savedInstanceState A bundle containing previously saved
     * instance state, if available.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater used to inflate the fragment layout.
     * @param container The parent view that will contain the fragment's UI.
     * @param savedInstanceState A bundle containing previously saved state.
     * @return The root View of the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * This method is called after onCreateView().
     * It initializes the ViewModel, configures the UI and sets up
     * the RecyclerView to display restaurant reviews.
     *
     * @param view The View returned by onCreateView().
     * @param savedInstanceState A bundle containing previously saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupUI();
        setupRecyclerView();
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        reviewsViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
    }

    /**
     * Sets up the UI components, including the status bar,
     * toolbar navigation, user profile image and review submission button.
     */
    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );
        window.setStatusBarColor(Color.TRANSPARENT);

        WindowCompat.getInsetsController(
                window,
                window.getDecorView()
        ).setAppearanceLightStatusBars(true);

        binding.reviewsToolbar.setNavigationOnClickListener(
                v -> getParentFragmentManager().popBackStack()
        );

        Glide.with(binding.tvImageReviewAvatar)
                .load("https://xsgames.co/randomusers/assets/avatars/female/78.jpg")
                .circleCrop()
                .into(binding.tvImageReviewAvatar);

        binding.buttonSubmitReview.setOnClickListener(
                v -> submitReview()
        );
    }

    /**
     * Initializes the RecyclerView and its adapter.
     * Observes the restaurant reviews from the ViewModel and updates
     * the displayed list when the data changes.
     */
    private void setupRecyclerView() {
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        binding.recyclerReviews.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        binding.recyclerReviews.setAdapter(reviewAdapter);

        reviewsViewModel.getReviews().observe(
                getViewLifecycleOwner(),
                reviews -> {
                    if (reviews != null) {
                        reviewAdapter.setReviews(reviews);
                    }
                }
        );
    }


    /**
     * Retrieves the comment and rating entered by the user.
     * Creates a new review and sends it to the ViewModel for validation
     * and submission.
     *
     * If the review is valid, the form is reset and the list scrolls
     * to the first position. Otherwise, an error message is displayed.
     */
    private void submitReview() {
        // Get
        String comment = binding.etReviewComment.getText().toString().trim();
        int rate = Math.round(binding.ratingBarReview.getRating());

        // Create the review
        Review review = new Review(
                "Manon Garcia",
                "https://xsgames.co/randomusers/assets/avatars/female/78.jpg",
                comment,
                rate
        );

        // Send the review to the ViewModel
        if (reviewsViewModel.addReview(review)) {
            binding.etReviewComment.setText("");
            binding.ratingBarReview.setRating(0);
            binding.recyclerReviews.scrollToPosition(0);
        } else {
            Toast.makeText(
                    requireContext(),
                    "Veuillez saisir un commentaire et une note.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}