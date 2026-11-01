
package com.example.jobportal.controller;

import com.example.jobportal.entity.Job;
import com.example.jobportal.service.JobService;
import com.example.jobportal.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import com.example.jobportal.dto.JobResponse;
import com.example.jobportal.dto.JobRequest;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;






@RestController
@RequestMapping("/api/jobs")
@Tag(
        name = "Job Management",
        description = "APIs for managing job postings"
)




public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(
            summary = "Create a new job",
            description = "Creates and saves a new job posting"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job details")
    })

    // Add a new job
    @PostMapping
    public JobResponse createJob(@Valid @RequestBody JobRequest request) {
        return jobService.createJob(request);
    }

    // Get all jobs
    @Operation(
            summary = "Get all jobs",
            description = "Retrieves all available job postings")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Jobs retrieved successfully")
    })
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @Operation(
            summary = "Delete a job",
            description = "Deletes a job posting using its ID"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update a job",
            description = "Updates an existing job posting using its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid job details"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })

    @PutMapping("/{id}")
    public JobResponse updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request) {

        return jobService.updateJob(id, request);
    }



    // Get job by ID
    @Operation(
            summary = "Get job by ID",
            description = "Retrieves a job posting using its ID"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job found successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // Search jobs by title
    @Operation(
            summary = "Search jobs by title",
            description = "Searches job postings based on the job title"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    })
    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String title) {
        return jobService.searchJobs(title);
    }

    @Operation(
            summary = "Search jobs by company",
            description = "Retrieves job postings based on company name"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company search results retrieved successfully")
    })

    @GetMapping("/search/company")
    public List<Job> searchByCompany(@RequestParam String company) {
        return jobService.searchByCompany(company);
    }


    @Operation(
            summary = "Search jobs by location",
            description = "Retrieves job postings based on location"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location search results retrieved successfully")
    })
    @GetMapping("/search/location")
    public List<Job> searchByLocation(@RequestParam String location) {
        return jobService.searchByLocation(location);
    }


    @Operation(
            summary = "Search jobs by salary",
            description = "Retrieves job postings based on salary range"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salary filter results retrieved successfully")
    })
    @GetMapping("/search/salary")
    public List<Job> filterBySalary(
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary) {

        return jobService.filterBySalary(minSalary, maxSalary);
    }



    @Operation(
            summary = "Advanced job search",
            description = "Search jobs using title, company, location and salary range"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Advanced search results retrieved successfully")
    })
    @GetMapping("/advanced-search")
    public List<Job> advancedSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary) {

        return jobService.advancedSearch(
                title, company, location, minSalary, maxSalary);
    }


    @Operation(
            summary = "Get jobs with pagination",
            description = "Retrieves job postings in pages with configurable page number and page size"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated jobs retrieved successfully")
    })

    @GetMapping("/page")
    public Page<Job> getAllJobs(Pageable pageable) {
        return jobService.getAllJobs(pageable);
    }

    @Operation(
            summary = "Sort jobs",
            description = "Retrieves job postings sorted by a selected field and direction"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sorted jobs retrieved successfully")
    })

    @GetMapping("/sort")
    public List<Job> sortJobs(
            @RequestParam(defaultValue = "salary") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return jobService.sortJobs(sort);
    }

    @Operation(
            summary = "Get paginated and sorted jobs",
            description = "Retrieves a page of job postings sorted by a selected field and direction"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated and sorted jobs retrieved successfully")
    })

    @GetMapping("/page-sort")
    public Page<Job> getJobsWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "salary") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return jobService.getAllJobs(pageable);
    }

    @Operation(
            summary = "Get job response by ID",
            description = "Retrieves job details using the response DTO"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job response retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })

    @GetMapping("/{id}/dto")
    public JobResponse getJobDTO(@PathVariable Long id) {
        return jobService.getJobResponseById(id);
    }





}
