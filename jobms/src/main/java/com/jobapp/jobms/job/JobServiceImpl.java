package com.jobapp.jobms.job;

import com.jobapp.jobms.job.dto.CompanyWithReviewsDTO;
import com.jobapp.jobms.job.dto.JobWithCompanyDTO;
import com.jobapp.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService{

    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    //    private List<Job> jobs = new ArrayList<>();
//    private Long nextId = 1L;

    @Override
    public List<JobWithCompanyDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());

//        RestTemplate restTemplate = new RestTemplate();
//
//        for (Job job : jobs){
//            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
//            jobWithCompanyDTO.setJob(job);
//
//            Company company = restTemplate.getForObject(
//                    "http://localhost:8081/companies/" + job.getCompanyId(),
//                    Company.class);
//
//            jobWithCompanyDTO.setCompany(company);
//            jobWithCompanyDTOs.add(jobWithCompanyDTO);
//        }
//        return jobWithCompanyDTOs;
    }

    private JobWithCompanyDTO convertToDto(Job job){

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);

        RestTemplate restTemplate = new RestTemplate();

        CompanyWithReviewsDTO CompanyWithReviewsDTO = restTemplate.getForObject(
                "http://localhost:8081/companies/" + job.getCompanyId(),
                CompanyWithReviewsDTO.class);

        jobWithCompanyDTO.setReviewList(CompanyWithReviewsDTO.getReviewList());
        jobWithCompanyDTO.setCompany(CompanyWithReviewsDTO.getCompany());
        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
//        job.setId(nextId++);
//        jobs.add(job);
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {
//        for(Job job : jobs){
//            if(job.getId().equals(id))
//                return job;
//        }
//        return null;

        Job job = jobRepository.findById(id).orElse(null);
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();

        if(job != null) {
            jobWithCompanyDTO.setJob(job);
            RestTemplate restTemplate = new RestTemplate();
            CompanyWithReviewsDTO companyWithReviewsDTO = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), CompanyWithReviewsDTO.class);
            jobWithCompanyDTO.setCompany(companyWithReviewsDTO.getCompany());
            jobWithCompanyDTO.setReviewList(companyWithReviewsDTO.getReviewList());
        }
        return jobWithCompanyDTO;
    }

    @Override
    public boolean deleteJob(Long id) {
//        jobs.removeIf(job -> job.getId().equals(id));
//        Iterator<Job> iterator = jobs.iterator();
//        while (iterator.hasNext()){
//            Job job = iterator.next();
//            if(job.getId().equals(id)) {
//                iterator.remove();
//                return true;
//            }
//        }
//        return false;
        try{
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
//        for(Job job : jobs){
//            if(job.getId().equals(id)){
//                job.setTitle(updatedJob.getTitle());
//                job.setDescription(updatedJob.getDescription());
//                job.setMinSalary(updatedJob.getMinSalary());
//                job.setMaxSalary(updatedJob.getMaxSalary());
//                job.setLocation(updatedJob.getLocation());
//                return true;
//            }
//        }
//        return false;
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
