package com.vigyanshaala.controller.jobPortalController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.StudentServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/job/student")
public class StudentController {
    @Autowired
    StudentServices studentServices;

    @Autowired
    UserController userController;
    @Autowired
    EntitlementController entitlementController;
    @ApiOperation(value = "Get all jobs from the job table without a filter", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all jobs.")
    @GetMapping(value = "/job/all", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getAllJobs(@RequestHeader("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                String email= idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = studentServices.getAllJobs();
                }else{
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Student/Admin role is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            }
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in getAllJobs controller : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
        return responseEntity;

    }

    @ApiOperation(value = "Get all jobs from the job table without a filter", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all jobs.")
    @GetMapping(value="/job/{id}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getJobByID(@RequestHeader("Authorization") String bearerToken,@PathVariable String id){
        ResponseEntity responseEntity;
        Response response=new Response();
        try {
            GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = studentServices.getJobByID(id);
                } else {
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Student/Admin role is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            } else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            log.error("Exception occurred while getting all the jobs ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting all the jobs"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get all active jobs from the job table without a filter", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all jobs.")
    @GetMapping(value = "/job/active", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getActiveJobs(@RequestHeader("Authorization") String bearerToken, @RequestParam(value="pageNumber", defaultValue="0",required=false) Integer pageNumber) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                String email= idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = studentServices.getActiveJobs(pageNumber);
                }else{
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Student/Admin role is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            }
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in getAllJobs controller : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        return responseEntity;

    }
}
