package com.jobapp.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService service){
        this.reviewService = service;
    }

    //get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){

        List<Review> reviews = reviewService.getAllReviews(companyId);
        if(reviews.isEmpty())
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    //get review by id
    @GetMapping(path = "/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){

        Review review = reviewService.getReviewById(reviewId);
        if(review != null)
            return new ResponseEntity<>(review, HttpStatus.OK);
        return new ResponseEntity<>(new Review(), HttpStatus.NOT_FOUND);
    }

    // create a review
    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @RequestBody Review review){

        boolean isReviewSaved = reviewService.createReview(companyId, review);
        if(isReviewSaved)
            return new ResponseEntity<>("Review Created Successfully", HttpStatus.CREATED);
        return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
    }

    // update a review
    @PutMapping(path ="/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review){

        boolean updated = reviewService.updateReview(reviewId, review);
        if(updated)
            return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK);
        return new ResponseEntity<>("Review not Found", HttpStatus.NOT_FOUND);
    }

    // delete a review by id
    @DeleteMapping(path = "/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){

        boolean deleted = reviewService.deleteReview(reviewId);
        if(deleted)
            return new ResponseEntity<>("Review Deleted Successfully", HttpStatus.OK);
        return new ResponseEntity<>("Review not Found", HttpStatus.NOT_FOUND);
    }
}
