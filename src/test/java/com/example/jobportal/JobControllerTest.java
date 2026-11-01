package com.example.jobportal;

import com.example.jobportal.controller.JobController;
import com.example.jobportal.service.JobService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.jobportal.dto.JobRequest;
import com.example.jobportal.dto.JobResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.jobportal.entity.Job;
import java.util.List;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Test
    void createJob_shouldReturnJobResponse() {

        JobRequest request = new JobRequest();
        request.setTitle("Java Developer");
        request.setCompany("Google");
        request.setLocation("Bangalore");
        request.setSalary(1200000.0);

        JobResponse expected = new JobResponse(
                1L,
                "Java Developer",
                "Google"
        );

        when(jobService.createJob(request)).thenReturn(expected);

        JobResponse actual = jobController.createJob(request);

        assertEquals(1L, actual.getId());
        assertEquals("Java Developer", actual.getTitle());
        assertEquals("Google", actual.getCompany());

        verify(jobService).createJob(request);
    }

    @Test
    void getAllJobs_shouldReturnAllJobs() {

        Job job1 = new Job();
        job1.setTitle("Java Developer");
        job1.setCompany("Google");

        Job job2 = new Job();
        job2.setTitle("Python Developer");
        job2.setCompany("Microsoft");

        when(jobService.getAllJobs()).thenReturn(List.of(job1, job2));

        List<Job> result = jobController.getAllJobs();

        assertEquals(2, result.size());
        assertEquals("Java Developer", result.get(0).getTitle());
        assertEquals("Python Developer", result.get(1).getTitle());

        verify(jobService).getAllJobs();
    }

    @Test
    void getJobById_shouldReturnJob() {

        Job job = new Job();
        job.setTitle("Java Developer");
        job.setCompany("Google");

        when(jobService.getJobById(1L)).thenReturn(job);

        Job result = jobController.getJobById(1L);

        assertNotNull(result);
        assertEquals("Java Developer", result.getTitle());
        assertEquals("Google", result.getCompany());

        verify(jobService).getJobById(1L);
    }

    @Test
    void deleteJob_shouldDeleteJob() {

        doNothing().when(jobService).deleteJob(1L);

        ResponseEntity<Void> result = jobController.deleteJob(1L);

        assertNotNull(result);

        verify(jobService).deleteJob(1L);
    }

    @Test
    void updateJob_shouldReturnUpdatedJob() {

        JobRequest request = new JobRequest();
        request.setTitle("Senior Java Developer");
        request.setCompany("Amazon");
        request.setLocation("Bangalore");
        request.setSalary(1800000.0);

        JobResponse expected = new JobResponse(
                1L,
                "Senior Java Developer",
                "Amazon"
        );

        when(jobService.updateJob(1L, request)).thenReturn(expected);

        JobResponse result = jobController.updateJob(1L, request);

        assertEquals(1L, result.getId());
        assertEquals("Senior Java Developer", result.getTitle());
        assertEquals("Amazon", result.getCompany());

        verify(jobService).updateJob(1L, request);
    }

    @Test
    void getJobResponseById_shouldReturnDTO() {

        JobResponse expected = new JobResponse(
                1L,
                "Java Developer",
                "Google"
        );

        when(jobService.getJobResponseById(1L)).thenReturn(expected);

        JobResponse result = jobController.getJobDTO(1L);

        assertEquals(1L, result.getId());
        assertEquals("Java Developer", result.getTitle());
        assertEquals("Google", result.getCompany());

        verify(jobService).getJobResponseById(1L);
    }

}