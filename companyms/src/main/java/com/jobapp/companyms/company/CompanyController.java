package com.jobapp.companyms.company;

import com.jobapp.companyms.company.dto.CompanyWithReviewsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService service){
        this.companyService = service;
    }

    // get all companies
    @GetMapping(path = "/")
    public ResponseEntity<List<CompanyWithReviewsDTO>> getAllCompanies(){
        List<CompanyWithReviewsDTO> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // get company by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<CompanyWithReviewsDTO> getCompanyById(@PathVariable Long id){

        CompanyWithReviewsDTO company = companyService.getCompanyById(id);
        if(company != null)
            return new ResponseEntity<>(company, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // create a company record in db
    @PostMapping(path = "/")
    public ResponseEntity<String> createCompany(@RequestBody Company company){

        companyService.createCompany(company);
        return new ResponseEntity<>("Company Created Successfully", HttpStatus.CREATED);
    }

    // update the company details
    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company){

        boolean updated = companyService.updateCompany(id, company);
        if(updated)
            return new ResponseEntity<>("Company Updated Successfully With Id: " + id, HttpStatus.OK);
        return new ResponseEntity<>("Company does not Exists With Id: " + id, HttpStatus.NOT_FOUND);
    }

    // delete the company details by id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){

        boolean deleted = companyService.deleteCompany(id);
        if(deleted)
            return new ResponseEntity<>("Company Deleted Successfully With id: " + id, HttpStatus.OK);
        return new ResponseEntity<>("Cannot Delete the Company With id: " + id, HttpStatus.NOT_FOUND);
    }
}
