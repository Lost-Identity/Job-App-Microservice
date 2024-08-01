package com.jobapp.jobms.job;

import com.jobapp.jobms.job.dto.JobWithCompanyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    // Get all jobs
    @GetMapping(path = "/")
    public ResponseEntity<List<JobWithCompanyDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    // Creating a job
    @PostMapping(path = "/")
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
    }

    // Get job by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id){
        JobWithCompanyDTO job = jobService.getJobById(id);
        if(job != null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete job by id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean deleted = jobService.deleteJob(id);
        if(deleted)
            return new ResponseEntity<>("Job Deleted Successfully with id: " + id, HttpStatus.OK);
        return new ResponseEntity<>("Job not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    // Update the existing job
    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateJobDetails(@PathVariable Long id, @RequestBody Job job){
        boolean updated = jobService.updateJob(id, job);
        if(updated)
            return new ResponseEntity<>("Job has been updated", HttpStatus.OK);
        return new ResponseEntity<>("job doesn't exist with id: " + id, HttpStatus.NOT_FOUND);
    }
}
