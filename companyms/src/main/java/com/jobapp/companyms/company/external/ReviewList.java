package com.jobapp.companyms.company.external;

import java.util.ArrayList;
import java.util.List;

public class ReviewList {

    List<Review> reviewsList;

    public ReviewList(){
        reviewsList = new ArrayList<>();
    }

    public List<Review> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }
}
