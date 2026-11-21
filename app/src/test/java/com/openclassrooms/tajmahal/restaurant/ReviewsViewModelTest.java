package com.openclassrooms.tajmahal.restaurant;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.ReviewsViewModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;

public class ReviewsViewModelTest {
    RestaurantRepository repository = mock(RestaurantRepository.class);
    ReviewsViewModel viewModel = new ReviewsViewModel(repository);

    @Test
    public void test_getReviews_returnReviews() {
        // Given
        LiveData<List<Review>> reviews = new MutableLiveData<>(
                Arrays.asList(
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
                )
        );

        when(repository.getReviews()).thenReturn(reviews);

        // When
        LiveData<List<Review>> result = viewModel.getReviews();

        // Then
        assertSame(reviews, result);
        assertEquals(2, result.getValue().size());
        verify(repository).getReviews();
    }

    @Test
    public void test_addReview_validReview_returnsReviews() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "Excellent restaurant",
                5
        );

        LiveData<List<Review>> reviews = new MutableLiveData<>(
                Arrays.asList(
                        review,
                        new Review(
                                "username1",
                                "avatar2.jpg",
                                "Excellent restaurant",
                                4
                        )
                )
        );

        when(repository.getReviews()).thenReturn(reviews);

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertSame(reviews, result);
        assertEquals(2, result.getValue().size());
        verify(repository).addReview(review);
    }

    @Test
    public void test_addReview_emptyComment_returnsNull() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "",
                5
        );

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }

    @Test
    public void test_addReview_blankComment_returnsNull() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "     ",
                5
        );

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }

    @Test
    public void test_addReview_noRating_returnsNull() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "Excellent restaurant",
                0
        );

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }

    @Test
    public void test_addReview_ratingAboveFive_returnsNull() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                "Excellent restaurant",
                6
        );

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }

    @Test
    public void test_addReview_nullReview_returnNull() {
        // Given
        Review review = null;

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }

    @Test
    public void test_addReview_nullComment_returnsNull() {

        // Given
        Review review = new Review(
                "Manon Garcia",
                "avatar1.jpg",
                null,
                6
        );

        // When
        LiveData<List<Review>> result = viewModel.addReview(review);

        // Then
        assertNull(result);
        verifyNoInteractions(repository);
    }
}
