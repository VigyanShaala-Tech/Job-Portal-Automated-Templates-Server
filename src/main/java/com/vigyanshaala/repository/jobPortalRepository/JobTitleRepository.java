package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.jobPortalEntity.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTitleRepository extends JpaRepository<JobTitle,String> {
}
