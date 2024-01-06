package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.jobPortalEntity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ExpiredJobsRepository extends JpaRepository<Job,String> {
        @Modifying
        @Transactional
        @Query(value="UPDATE Job a set is_active='N' where a.expiry_date<:expiryDate and is_active='Y'", nativeQuery = true)
        void softdeleteJobs (
            @Param("expiryDate") String expiryDate);

}

