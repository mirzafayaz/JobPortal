package com.example.jobportal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.service.JobService;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.jobportal.dto.JobRequest;
import com.example.jobportal.dto.JobResponse;
import com.example.jobportal.entity.Job;
import java.util.Optional;
import com.example.jobportal.exception.ResourceNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@ExtendWith(MockitoExtension.class)
class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    void createJob_shouldReturnJobResponse() {

        JobRequest request = new JobRequest();
        request.setTitle("Java Developer");
        request.setCompany("Google");
        request.setLocation("Bangalore");
        request.setSalary(1200000.0);

        Job savedJob = new Job();
        savedJob.setId(1L);
        savedJob.setTitle("Java Developer");
        savedJob.setCompany("Google");
        savedJob.setLocation("Bangalore");
        savedJob.setSalary(1200000.0);

        when(jobRepository.save(any(Job.class))).thenReturn(savedJob);

        JobResponse response = jobService.createJob(request);

        assertEquals(1L, response.getId());
        assertEquals("Java Developer", response.getTitle());
        assertEquals("Google", response.getCompany());

        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void getJobById_shouldReturnJob() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setCompany("Google");
        job.setLocation("Bangalore");
        job.setSalary(1200000.0);

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        Job result = jobService.getJobById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Java Developer", result.getTitle());

        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void getJobById_shouldThrowExceptionWhenJobNotFound() {

        when(jobRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobService.getJobById(999L)
        );

        verify(jobRepository, times(1)).findById(999L);
    }

    @Test
    void getAllJobs_shouldReturnAllJobs() {

        Job job1 = new Job();
        job1.setId(1L);
        job1.setTitle("Java Developer");

        Job job2 = new Job();
        job2.setId(2L);
        job2.setTitle("Python Developer");

        when(jobRepository.findAll())
                .thenReturn(Arrays.asList(job1, job2));

        List<Job> result = jobService.getAllJobs();

        assertEquals(2, result.size());
        assertEquals("Java Developer", result.get(0).getTitle());
        assertEquals("Python Developer", result.get(1).getTitle());

        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void updateJob_shouldUpdateExistingJob() {

        Job existingJob = new Job();
        existingJob.setId(1L);
        existingJob.setTitle("Java Developer");
        existingJob.setCompany("Google");
        existingJob.setLocation("Bangalore");
        existingJob.setSalary(1000000.0);

        JobRequest request = new JobRequest();
        request.setTitle("Senior Java Developer");
        request.setCompany("Microsoft");
        request.setLocation("Hyderabad");
        request.setSalary(1500000.0);

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(existingJob));

        when(jobRepository.save(any(Job.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        JobResponse response = jobService.updateJob(1L, request);

        assertEquals("Senior Java Developer", response.getTitle());
        assertEquals("Microsoft", response.getCompany());

        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    void deleteJob_shouldDeleteExistingJob() {

        jobService.deleteJob(1L);

        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchJobs_shouldReturnMatchingJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setCompany("Google");

        when(jobRepository.findByTitleContainingIgnoreCase("Java"))
                .thenReturn(List.of(job));

        List<Job> result = jobService.searchJobs("Java");

        assertEquals(1, result.size());
        assertEquals("Java Developer", result.get(0).getTitle());

        verify(jobRepository, times(1))
                .findByTitleContainingIgnoreCase("Java");
    }

    @Test
    void getJobResponseById_shouldReturnDTO() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setCompany("Google");

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        JobResponse response = jobService.getJobResponseById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Java Developer", response.getTitle());
        assertEquals("Google", response.getCompany());

        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    void searchByCompany_shouldReturnMatchingJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Backend Developer");
        job.setCompany("Microsoft");

        when(jobRepository.findByCompanyContainingIgnoreCase("Microsoft"))
                .thenReturn(List.of(job));

        List<Job> result = jobService.searchByCompany("Microsoft");

        assertEquals(1, result.size());
        assertEquals("Microsoft", result.get(0).getCompany());

        verify(jobRepository, times(1))
                .findByCompanyContainingIgnoreCase("Microsoft");
    }

    @Test
    void searchByLocation_shouldReturnMatchingJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Backend Developer");
        job.setCompany("Amazon");
        job.setLocation("Bangalore");

        when(jobRepository.findByLocationContainingIgnoreCase("Bangalore"))
                .thenReturn(List.of(job));

        List<Job> result = jobService.searchByLocation("Bangalore");

        assertEquals(1, result.size());
        assertEquals("Bangalore", result.get(0).getLocation());

        verify(jobRepository, times(1))
                .findByLocationContainingIgnoreCase("Bangalore");
    }

    @Test
    void filterBySalary_shouldReturnJobsWithinRange() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setSalary(1200000.0);

        when(jobRepository.findBySalaryBetween(500000.0, 1500000.0))
                .thenReturn(List.of(job));

        List<Job> result =
                jobService.filterBySalary(500000.0, 1500000.0);

        assertEquals(1, result.size());
        assertEquals(1200000.0, result.get(0).getSalary());

        verify(jobRepository, times(1))
                .findBySalaryBetween(500000.0, 1500000.0);
    }

    @Test
    void advancedSearch_shouldReturnMatchingJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setCompany("Google");
        job.setLocation("Bangalore");
        job.setSalary(1200000.0);

        when(jobRepository.advancedSearch(
                "Java", "Google", "Bangalore",
                500000.0, 1500000.0))
                .thenReturn(List.of(job));

        List<Job> result = jobService.advancedSearch(
                "Java", "Google", "Bangalore",
                500000.0, 1500000.0);

        assertEquals(1, result.size());
        assertEquals("Google", result.get(0).getCompany());
        assertEquals("Bangalore", result.get(0).getLocation());

        verify(jobRepository, times(1)).advancedSearch(
                "Java", "Google", "Bangalore",
                500000.0, 1500000.0);
    }

    @Test
    void sortJobs_shouldReturnSortedJobs() {

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");
        job.setSalary(1200000.0);

        Sort sort = Sort.by(Sort.Direction.ASC, "salary");

        when(jobRepository.findAll(sort))
                .thenReturn(List.of(job));

        List<Job> result = jobService.sortJobs(sort);

        assertEquals(1, result.size());
        assertEquals(1200000.0, result.get(0).getSalary());

        verify(jobRepository, times(1)).findAll(sort);
    }

    @Test
    void getAllJobsWithPagination_shouldReturnPageOfJobs() {

        Pageable pageable = PageRequest.of(0, 5);

        Job job = new Job();
        job.setId(1L);
        job.setTitle("Java Developer");

        Page<Job> jobPage = new PageImpl<>(List.of(job));

        when(jobRepository.findAll(pageable))
                .thenReturn(jobPage);

        Page<Job> result = jobService.getAllJobs(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Java Developer", result.getContent().get(0).getTitle());

        verify(jobRepository, times(1)).findAll(pageable);
    }



}
