package com.jobapp.companyms.company;

import com.jobapp.companyms.company.dto.CompanyWithReviewsDTO;
import com.jobapp.companyms.company.external.Review;
import com.jobapp.companyms.company.external.ReviewList;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{

    private CompanyRepository companyRepository;

    private CompanyServiceImpl(CompanyRepository companyRepo){
        this.companyRepository = companyRepo;
    }

    @Override
    public List<CompanyWithReviewsDTO> getAllCompanies() {

        List<Company> companyList = companyRepository.findAll();
        List<CompanyWithReviewsDTO> companyWithReviewsDTOList = new ArrayList<>();

        for (Company company : companyList){
            CompanyWithReviewsDTO companyWithReviewsDTO = new CompanyWithReviewsDTO();
            companyWithReviewsDTO.setCompany(company);

            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<List<Review>> claimResponse = restTemplate.exchange(
                        "http://localhost:8083/reviews?companyId=" + company.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Review>>() {});
                if(claimResponse != null && claimResponse.hasBody()){
                    List<Review> reviewList  = claimResponse.getBody();
                    companyWithReviewsDTO.setReviewList(reviewList);
                }
            } catch (RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            companyWithReviewsDTOList.add(companyWithReviewsDTO);
        }

        return companyWithReviewsDTOList;
    }

    @Override
    public CompanyWithReviewsDTO getCompanyById(Long id) {

        Company company = companyRepository.findById(id).orElse(null);
        CompanyWithReviewsDTO companyWithReviewsDTO = new CompanyWithReviewsDTO();
        companyWithReviewsDTO.setCompany(company);

        RestTemplate restTemplate = new RestTemplate();
        if(company != null){

            try {
                ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                        "http://localhost:8083/reviews?companyId=" + company.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Review>>() {
                        });
                if (reviewResponse != null && reviewResponse.hasBody()) {
                    List<Review> reviewList = reviewResponse.getBody();
                    companyWithReviewsDTO.setReviewList(reviewList);
                }
            } catch (RestClientException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return companyWithReviewsDTO;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
//        try{
//            companyRepository.deleteById(id);
//            return true;
//        } catch (Exception e){
//            return false;
//        }
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCompany(Long id, Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()){
            Company company1 = companyOptional.get();
            company1.setDescription(company.getDescription());
            company1.setName(company.getName());
            companyRepository.save(company1);
            return true;
        }
        return false;
    }
}
