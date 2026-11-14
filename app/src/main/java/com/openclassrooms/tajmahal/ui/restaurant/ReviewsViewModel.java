package com.openclassrooms.tajmahal.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReviewsViewModel extends ViewModel {
    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public ReviewsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    public LiveData<List<Review>> getReviews() {
        return restaurantRepository.getReviews();
    }

    /**
     * Validates and adds a restaurant review.
     *
     * @param review The review to add.
     * @return True if the review is valid and added.
     */
    public boolean addReview(Review review) {
        if (review == null
                || review.getComment() == null
                || review.getComment().trim().isEmpty()
                || review.getRate() < 1
                || review.getRate() > 5) {
            return false;
        }

        restaurantRepository.addReview(review);
        return true;
    }

}
