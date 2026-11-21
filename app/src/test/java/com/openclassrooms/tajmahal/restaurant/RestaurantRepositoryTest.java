package com.openclassrooms.tajmahal.restaurant;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RestaurantRepositoryTest {

    RestaurantApi restaurantApi = mock(RestaurantApi.class);
    Restaurant restaurant = mock(Restaurant.class);

    @Test
    public void test_getRestaurant_returnsRestaurant() {

        // Given
        when(restaurantApi.getRestaurant()).thenReturn(restaurant);

        RestaurantRepository repository = new RestaurantRepository(restaurantApi);

        // When
        LiveData<Restaurant> result = repository.getRestaurant();

        // Then
        assertEquals(restaurant, result.getValue());
        verify(restaurantApi).getRestaurant();
    }

    @Test
    public void test_getReview_returns_InitialReview() {
        // Given
        List<Review> reviews = Arrays.asList(
                new Review(
                        "username1",
                        "avatar1.jpg",
                        "Excellent restaurant",
                        5
                ),
                new Review(
                        "username2",
                        "avatar2.jpg",
                        "Goog restaurant",
                        4
                )
        );

        when(restaurantApi.getReviews()).thenReturn(reviews);

        RestaurantRepository repository = new RestaurantRepository(restaurantApi);

        // When
        LiveData<List<Review>> result = repository.getReviews();

        // Then
        assertEquals(reviews, result.getValue());
        assertEquals(2, result.getValue().size());
        verify(restaurantApi).getReviews();
    }

    @Test
    public void test_getReviews_emptyList_returnsEmptyList() {

        // Given
        List<Review> reviews = Collections.emptyList();

        when(restaurantApi.getReviews()).thenReturn(reviews);

        RestaurantRepository repository = new RestaurantRepository(restaurantApi);

        // When
        LiveData<List<Review>> result = repository.getReviews();

        // Then
        assertEquals(0, result.getValue().size());
        verify(restaurantApi).getReviews();
    }

    @Test
    public void test_addReview_callsApi() {

        // Given
        RestaurantRepository repository = new RestaurantRepository(restaurantApi);

        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "Excellent restaurant",
                5
        );

        // When
        repository.addReview(review);

        // Then
        verify(restaurantApi).addReview(review);
    }
}
