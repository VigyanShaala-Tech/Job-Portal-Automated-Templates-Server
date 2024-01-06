package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "job")

public class Job {

    private @Id
    String jobId;

    public String getHrEmail() {
        return hrEmail;
    }

    public void setHrEmail(String hrEmail) {
        this.hrEmail = hrEmail;
    }

    private LocalDate postingDate;

    private LocalDate expiryDate;
    private String isActive;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Company company;

    @ManyToOne(cascade = CascadeType.MERGE)
    private JobTitle jobTitle;

    @ManyToOne(cascade = CascadeType.MERGE)
    private EducationLevel educationLevel;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Industry industry;

    @ManyToOne(cascade = CascadeType.MERGE)
    private WorkMode workMode;

    @Lob
    private String jobDescription;
    private String hrEmail;

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    @OneToOne(cascade = CascadeType.MERGE)
    private Questionnaire questionnaire;

    @ManyToOne(cascade = CascadeType.MERGE)
    private JobLocation jobLocation;

    public Job() {
    }

    public Job(String jobId, LocalDate postingDate, LocalDate expiryDate, String isActive, Company company, JobTitle jobTitle, String jobDescription, JobLocation jobLocation, Questionnaire questionnaire, WorkMode workMode, EducationLevel educationLevel, Industry industry) {
        this.jobId = jobId;
        this.postingDate = postingDate;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
        this.company = company;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.questionnaire = questionnaire;
        this.workMode = workMode;
        this.industry = industry;
        this.educationLevel = educationLevel;
    }


    public Job(Company company, JobTitle jobTitle, JobLocation jobLocation, WorkMode workMode, EducationLevel educationLevel, Industry industry) {
        this.company = company;
        this.jobTitle = jobTitle;
        this.jobLocation = jobLocation;
        this.workMode = workMode;
        this.industry = industry;
        this.educationLevel = educationLevel;
    }

    public Job(String jobId, LocalDate postingDate, LocalDate expiryDate, String isActive, Company company, JobTitle jobTitle, String jobDescription, JobLocation jobLocation, Questionnaire questionnaire, WorkMode workMode, EducationLevel educationLevel, Industry industry, String hrEmail) {
        this.jobId = jobId;
        this.postingDate = postingDate;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
        this.company = company;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobLocation = jobLocation;
        this.questionnaire = questionnaire;
        this.workMode = workMode;
        this.industry = industry;
        this.educationLevel = educationLevel;
        this.hrEmail = hrEmail;
    }


}




