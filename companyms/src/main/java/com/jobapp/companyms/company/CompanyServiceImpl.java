package com.jobapp.companyms.company;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyServiceImpl(CompanyRepository companyRepo){
        this.companyRepository = companyRepo;
    }

    @Override
    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }

//    private CompanyWithReviewsDTO convertToDto(Company company){
//
//        CompanyWithReviewsDTO companyWithReviewsDTO = new CompanyWithReviewsDTO();
//        companyWithReviewsDTO.setCompany(company);
////        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            ResponseEntity<List<Review>> claimResponse = restTemplate.exchange(
//                    "http://reviewms:8083/reviews?companyId=" + company.getId(),
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Review>>() {});
//
//            if(claimResponse.hasBody()){
//                List<Review> reviewList  = claimResponse.getBody();
//                companyWithReviewsDTO.setReviewList(reviewList);
//            }
//        } catch (RestClientException e) {
//            e.printStackTrace();
//        }
//        return companyWithReviewsDTO;

//    }

    @Override
    public Company getCompanyById(Long id) {

        return companyRepository.findById(id).orElse(null);
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
