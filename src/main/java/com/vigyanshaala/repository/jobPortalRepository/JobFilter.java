package com.vigyanshaala.repository.jobPortalRepository;

import lombok.Value;

import java.time.LocalDate;

@Value
public class JobFilter {
    String location;
    String company;
    String jobTitle;
    LocalDate fromDate;
    String workMode;
    String educationLevel;
    String industry;
}