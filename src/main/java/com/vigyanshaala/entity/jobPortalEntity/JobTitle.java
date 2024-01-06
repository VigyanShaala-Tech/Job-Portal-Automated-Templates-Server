package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This is the job_title table which stores all the job titles that the admin has submitted
 */
@Entity
@Table(name = "job_title")

public class JobTitle {

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Id
    private String jobTitleId;
    private String jobTitle;

    public JobTitle() {
    }

    public JobTitle(String jobTitleId, String jobTitle) {
        this.jobTitleId = jobTitleId;
        this.jobTitle = jobTitle;
    }


}
