package com.vigyanshaala.repository.jobPortalRepository;

import com.vigyanshaala.entity.jobPortalEntity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomJobRepository {
    Page<Job> fetchAll(JobFilter filterParams, Pageable p);
}
