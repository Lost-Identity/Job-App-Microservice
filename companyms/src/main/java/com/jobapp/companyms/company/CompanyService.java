package com.jobapp.companyms.company;

import com.jobapp.companyms.company.dto.CompanyWithReviewsDTO;

import java.util.List;

public interface CompanyService {

    List<CompanyWithReviewsDTO> getAllCompanies();

    CompanyWithReviewsDTO getCompanyById(Long id);

    void createCompany(Company company);

    boolean deleteCompany(Long id);

    boolean updateCompany(Long id, Company company);
}
