package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.jobPortalEntity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, String> {

    List<JobApplication> findByisJobApplicationPostedToHr(boolean isJobApplicationPostedToHr);

    List<JobApplication> findBystudentId(String studentId);
}
