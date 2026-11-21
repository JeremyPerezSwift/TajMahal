package com.openclassrooms.tajmahal.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DetailsViewModelTest {

    RestaurantRepository repository = mock(RestaurantRepository.class);
    DetailsViewModel viewModel = new DetailsViewModel(repository);

    ///  Get

    @Test
    public void test_getTajMahalRestaurant_returnsRestaurant() {

        // Given
        Restaurant restaurant = mock(Restaurant.class);

        LiveData<Restaurant> restaurantLiveData =
                new MutableLiveData<>(restaurant);

        when(repository.getRestaurant()).thenReturn(restaurantLiveData);

        // When
        LiveData<Restaurant> result = viewModel.getTajMahalRestaurant();

        // Then
        assertSame(restaurantLiveData, result);
        assertEquals(restaurant, result.getValue());
        verify(repository).getRestaurant();
    }

    @Test
    public void test_getTajMahalReviews_returnsReviews() {

        // Given
        LiveData<List<Review>> reviews = new MutableLiveData<>(
                Arrays.asList(
                        new Review(
                                "Manon Garcia",
                                "avatar1.jpg",
                                "Excellent restaurant",
                                5
                        ),
                        new Review(
                                "username1",
                                "avatar2.jpg",
                                "Très bon restaurant",
                                4
                        )
                )
        );

        when(repository.getReviews()).thenReturn(reviews);

        // When
        LiveData<List<Review>> result = viewModel.getTajMahalReviews();

        // Then
        assertSame(reviews, result);
        assertEquals(2, result.getValue().size());
        verify(repository).getReviews();
    }

    ///  test_calculateAverageRating_

    @Test
    public void test_calculateAverageRating_returnsAverage() {

        // Given
        List<Review> reviews = Arrays.asList(
                new Review("username1", "avatar1.jpg", "Excellent", 5),
                new Review("username2", "avatar2.jpg", "Excellent", 5),
                new Review("username3", "avatar3.jpg", "Très bien", 4),
                new Review("username4", "avatar4.jpg", "Moyen", 2)
        );

        // When
        float result = viewModel.calculateAverageRating(reviews);

        // Then
        assertEquals(4f, result, 0.001f);
    }


    @Test
    public void test_calculateAverageRating_emptyList_returnsZero() {

        // Given
        List<Review> reviews = Collections.emptyList();

        // When
        float result = viewModel.calculateAverageRating(reviews);

        // Then
        assertEquals(0f, result, 0.001f);
    }

    @Test
    public void test_calculateAverageRating_nullList_returnsZero() {

        // Given
        List<Review> reviews = null;

        // When
        float result = viewModel.calculateAverageRating(reviews);

        // Then
        assertEquals(0f, result, 0.001f);
    }

    @Test
    public void test_calculateAverageRating_returnsDecimalAverage() {

        // Given
        List<Review> reviews = Arrays.asList(
                new Review("username1", "avatar1.jpg", "Excellent", 5),
                new Review("username2", "avatar2.jpg", "Très bien", 4)
        );

        // When
        float result = viewModel.calculateAverageRating(reviews);

        // Then
        assertEquals(4.5f, result, 0.001f);
    }

    /// test_getRatingPercentage

    @Test
    public void test_getRatingPercentage_returnsPercentage() {

        // Given
        List<Review> reviews = Arrays.asList(
                new Review("username1", "avatar1.jpg", "Excellent", 5),
                new Review("username2", "avatar2.jpg", "Excellent", 5),
                new Review("username3", "avatar3.jpg", "Très bien", 4),
                new Review("username4", "avatar4.jpg", "Moyen", 2)
        );

        // When
        int result = viewModel.getRatingPercentage(reviews, 5);

        // Then
        assertEquals(50, result);
    }


    @Test
    public void test_getRatingPercentage_emptyList_returnsZero() {

        // Given
        List<Review> reviews = Collections.emptyList();

        // When
        int result = viewModel.getRatingPercentage(reviews, 5);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void test_getRatingPercentage_nullList_returnsZero() {

        // Given
        List<Review> reviews = null;

        // When
        int result = viewModel.getRatingPercentage(reviews, 5);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void test_getRatingPercentage_noMatchingRating_returnsZero() {

        // Given
        List<Review> reviews = Arrays.asList(
                new Review("username1", "avatar1.jpg", "Excellent", 5),
                new Review("username2", "avatar2.jpg", "Très bien", 4)
        );

        // When
        int result = viewModel.getRatingPercentage(reviews, 2);

        // Then
        assertEquals(0, result);
    }

    @Test
    public void test_getRatingPercentage_roundsPercentage() {

        // Given
        List<Review> reviews = Arrays.asList(
                new Review("username1", "avatar1.jpg", "Excellent", 5),
                new Review("username2", "avatar2.jpg", "Excellent", 5),
                new Review("username3", "avatar3.jpg", "Très bien", 4)
        );

        // When
        int result = viewModel.getRatingPercentage(reviews, 5);

        // Then
        assertEquals(67, result);
    }


}
