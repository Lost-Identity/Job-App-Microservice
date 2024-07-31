package com.jobapp.reviewms.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    Review getReviewById(Long reviewId);

    boolean createReview(Long companyId, Review review);

    boolean updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
