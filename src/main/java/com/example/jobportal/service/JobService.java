
package com.example.jobportal.service;

import com.example.jobportal.entity.Job;
import com.example.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;
import com.example.jobportal.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.jobportal.dto.JobResponse;
import com.example.jobportal.dto.JobRequest;



@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponse createJob(JobRequest request) {

        Job job = convertToJob(request);

        Job savedJob = jobRepository.save(job);

        return new JobResponse(
                savedJob.getId(),
                savedJob.getTitle(),
                savedJob.getCompany()
        );
    }

    private Job convertToJob(JobRequest request) {

        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());

        return job;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    public JobResponse updateJob(Long id, JobRequest request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id));

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());

        Job updatedJob = jobRepository.save(job);

        return new JobResponse(
                updatedJob.getId(),
                updatedJob.getTitle(),
                updatedJob.getCompany()
        );
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        ));
    }

    public List<Job> searchJobs(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Job> searchByCompany(String company) {
        return jobRepository.findByCompanyContainingIgnoreCase(company);
    }

    public List<Job> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    public List<Job> filterBySalary(Double minSalary, Double maxSalary) {
        return jobRepository.findBySalaryBetween(minSalary, maxSalary);
    }

    public List<Job> advancedSearch(
            String title,
            String company,
            String location,
            Double minSalary,
            Double maxSalary) {

        return jobRepository.advancedSearch(
                title, company, location, minSalary, maxSalary);
    }

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> sortJobs(Sort sort) {
        return jobRepository.findAll(sort);
    }

    public JobResponse getJobResponseById(Long id) {

        Job job = getJobById(id);

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany()
        );
    }




}
