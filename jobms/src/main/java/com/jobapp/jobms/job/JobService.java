package com.jobapp.jobms.job;

import com.jobapp.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {

    List<JobWithCompanyDTO> findAll();

    void createJob(Job job);

    JobWithCompanyDTO getJobById(Long id);

    boolean deleteJob(Long id);

    boolean updateJob(Long id, Job job);
}
