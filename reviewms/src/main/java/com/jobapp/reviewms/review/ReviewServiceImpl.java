package com.jobapp.reviewms.review;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;


    public ReviewServiceImpl(ReviewRepository repo){
        this.reviewRepository = repo;
    }


    @Override
    public List<Review> getAllReviews(Long companyId) {

        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean createReview(Long companyId, Review review) {

        if(companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review != null){
            review.setReviewTitle(updatedReview.getReviewTitle());
            review.setReviewDescription(updatedReview.getReviewDescription());
            review.setReviewRating(updatedReview.getReviewRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(review != null){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
