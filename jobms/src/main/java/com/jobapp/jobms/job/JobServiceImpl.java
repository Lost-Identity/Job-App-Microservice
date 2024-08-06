package com.jobapp.jobms.job;

import com.jobapp.jobms.job.clients.CompanyClient;
import com.jobapp.jobms.job.clients.ReviewClient;
import com.jobapp.jobms.job.dto.JobDTO;
import com.jobapp.jobms.job.external.Company;
import com.jobapp.jobms.job.external.Review;
import com.jobapp.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private final CompanyClient companyClient;

    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }


    @Override
    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    private JobDTO convertToDto(Job job){

//        Company company = restTemplate.getForObject(
//                "http://companyms:8081/companies/" + job.getCompanyId(),
//                Company.class);

        Company company = companyClient.getCompany(job.getCompanyId());

//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapTOJobWithCompanyDto(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {

        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {

        Job job = jobRepository.findById(id).orElse(null);
        if(job != null)
            return convertToDto(job);
        return new JobDTO();

    }

    @Override
    public boolean deleteJob(Long id) {

        try{
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {

        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompanyId(updatedJob.getCompanyId());
            jobRepository.save(job);
            return true;
        }
        return false;
    }


}
