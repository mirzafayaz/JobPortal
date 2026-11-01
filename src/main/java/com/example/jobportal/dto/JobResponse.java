package com.example.jobportal.dto;

public class JobResponse {

    private Long id;
    private String title;
    private String company;

    public JobResponse(Long id, String title, String company) {
        this.id = id;
        this.title = title;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }
}
