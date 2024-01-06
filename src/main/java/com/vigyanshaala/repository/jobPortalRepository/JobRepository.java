package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.jobPortalEntity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, String>, CustomJobRepository {

    @Transactional
    @Query(value = "SELECT * FROM job j where j.job_id= :jobId", nativeQuery = true)
    public Job findByJobId(String jobId);

    @Transactional
    @Query(value = "SELECT\n" +
            "    *\n" +
            "FROM\n" +
            "    job AS j\n" +
            "  LEFT JOIN company AS j1 ON j.company_company_id = j1.company_id\n" +
            "    LEFT JOIN job_location AS j2 ON j.job_location_job_location_id = j2.job_location_id\n" +
            "\n" +
            "  LEFT JOIN job_title AS j3 ON j.job_title_job_title_id = j3.job_title_id where j1.company_name= :companyName AND j2.job_location= :jobLocation AND j3.job_title= :jobTitle AND j.job_description= :jobDescription AND j.is_active='Y' ;\n" +
            "   " , nativeQuery = true)
    public Job findDuplicateJob(String companyName, String jobLocation, String jobTitle, String jobDescription);

    @Transactional
    @Query(value = "SELECT * FROM job j where j.is_active= 'Y'", nativeQuery = true)
    public Page<Job> findActiveJobs(Pageable p);
}
