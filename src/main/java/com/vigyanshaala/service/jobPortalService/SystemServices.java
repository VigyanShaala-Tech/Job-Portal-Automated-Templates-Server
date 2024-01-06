package com.vigyanshaala.service.jobPortalService;

import org.springframework.http.ResponseEntity;

public interface SystemServices {

    ResponseEntity deleteExpiredJobs();

    ResponseEntity mailJobApplicationsToHr();
}
