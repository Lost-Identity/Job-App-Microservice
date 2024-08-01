package com.jobapp.companyms.company.dto;

import com.jobapp.companyms.company.Company;
import com.jobapp.companyms.company.external.Review;

import java.util.List;

public class CompanyWithReviewsDTO {

    private Company company;

    private List<Review> reviewList;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
}
