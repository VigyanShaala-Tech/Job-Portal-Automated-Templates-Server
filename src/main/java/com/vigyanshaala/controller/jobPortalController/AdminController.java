package com.vigyanshaala.controller.jobPortalController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.entity.jobPortalEntity.*;
import com.vigyanshaala.repository.jobPortalRepository.CustomJobRepositoryImpl;
import com.vigyanshaala.repository.jobPortalRepository.JobFilter;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.AdminServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * The following Admin Controller contains all the get and post calls for the Admin tasks
 * POST : saving the company details, job titles, job locations, job postings
 * GET : get company details,job titles, job locations
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/job/admin")
@Slf4j
public class AdminController {
    @Autowired
    AdminServices adminServices;
    @Autowired
    CustomJobRepositoryImpl customJobRepository;
    @Autowired
    UserController userController;

    @Autowired
    EntitlementController entitlementController;
    @ApiOperation(value = "Add work mode in the WorkMode table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value = "/workmode", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addWorkmode (@RequestHeader("Authorization") String bearerToken,@RequestBody WorkMode workmode) {
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("Admin")) {
                    response = adminServices.addWorkmode(workmode);
                }
                else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the Vigyanshaala Team");
                }
            } else throw new Exception("bearer token invalid");
        }
        catch(Exception e){
            log.info("Exception "+e.getMessage()+" occurred in addWorkMode controller");
            response.setStatusMessage("Exception "+e.getMessage()+" occurred in addWorkMode controller");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    @ApiOperation(value = "Get workmode list from the Workmode table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all workmodes.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value="/workmode/all", produces="application/json")
    ResponseEntity<Response> getWorkmodeList(@RequestHeader ("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = adminServices.getWorkmodeList();
                }
                else {
                log.error("You need admin or student role to perform this action");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }}
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured in get workmode controller : " +e );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
    @ApiOperation(value = "Add education level in the EducationLevel table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value="/educationLevel", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addEducationLevel(@RequestHeader("Authorization") String bearerToken,@RequestBody EducationLevel educationLevel) {
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
            String role = userController.getRole(email);
            log.info(role);
            if (role.equalsIgnoreCase("Admin")) {
                    response = adminServices.addEducationLevel(educationLevel);
            }
            else {
                log.error("You need admin role to perform this action");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
            }
        } else throw new Exception("bearer token is invalid ");
        }
        catch(Exception e){
            log.error("Exception occurred while adding educationLevel name : ", e.getMessage());
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding educationLevel name " + e);
        }
        return response;
    }


    @ApiOperation(value = "Get education Level list from the educationLevel table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all education levls.")
    @GetMapping(value="/educationLevel/all", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getEducationLevelList(@RequestHeader("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = adminServices.getEducationLevelList();
                }
                else {
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            } else throw new Exception ("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in getEducationLevelList controller : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return  responseEntity;
    }

    @ApiOperation(value = "Add industry in the Industry table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value="/industry", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addIndustry(@RequestHeader("Authorization") String bearerToken,@RequestBody Industry industry) {
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    response = adminServices.addIndustry(industry);
                } else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            } else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in addIndustry controller "+e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "Get industry list from the Industry table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all industry.")
    @GetMapping(value="/industry/all", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getIndustryList(@RequestHeader("Authorization") String bearerToken){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                    responseEntity = adminServices.getIndustryList();
                }
                else {
                log.error("You need admin or student role to perform this action");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Bearer token is invalid");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Add company details in the Company table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value="/company", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addCompany(@RequestHeader("Authorization") String bearerToken,@RequestBody Company company){
        Response response=new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                        response = adminServices.addCompany(company);
                } else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                    return response;
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Bearer token is invalid");
        }
        return response;
    }

    @ApiOperation(value = "Get company list from the Company table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all companies.")
    @GetMapping(value="/company/all", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getCompanyList(@RequestHeader("Authorization") String bearerToken){

        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
            String role= userController.getRole(email);
            log.info(role);
            if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                responseEntity = adminServices.getCompanyList();
            }
            else{
                log.error("You need admin or student role to perform this action");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }} else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in getCompanyList controller : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Add job title in the JobTitle table", notes = "Returns a response with status code 200 for successful addition in the table")
    @PostMapping(value="/title", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addJobTitle(@RequestHeader("Authorization") String bearerToken,@RequestBody JobTitle jobTitle){

        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("Admin")) {
                    response = adminServices.addJobTitle(jobTitle);
                }
                else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                    return response;}
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Bearer token is invalid");
        }
        return response;
    }

    @ApiOperation(value = "Get job title list from the JobTitle table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all job titles.")
    @GetMapping(value="/title/all", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getJobTitleList(@RequestHeader("Authorization") String bearerToken){
        ResponseEntity responseEntity;
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")){
                    responseEntity = adminServices.getJobTitleList();
                }else{
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }}
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in getJobTitleList controller" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }



    @ApiOperation(value = "Add job location in the JobLocation table", notes = "Returns a response with status code 200 for successful addition in the table")
    @PostMapping(value="/location", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addJobLocationList(@RequestHeader("Authorization") String bearerToken,@RequestBody JobLocation jobLocation){
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("Admin")) {
                    response = adminServices.addJobLocation(jobLocation);
                }else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in addJobLocationList : "+e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "Get job location list from the JobLocation table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all job locations.")
    @GetMapping(value="/location/all", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getJobLocationList(@RequestHeader("Authorization") String bearerToken){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
            String role= userController.getRole(email);
            log.info(role);
            if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
                responseEntity = adminServices.getJobLocationList();
            }else{
                log.error("You need admin or student role to perform this action");
                response.setStatusCode(HttpStatus.FORBIDDEN.value());
                response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }}else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting job location list" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Add job in the job table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value="/job",consumes="application/json", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addJob(@RequestHeader("Authorization") String bearerToken,@RequestBody Job job){
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("Admin")) {
                    response = adminServices.createJob(job);
                }
                else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in addJob controller : "+e.getMessage());
        }
        return response;
    }


    @ApiOperation(value = "Update job in the job table", notes = "Returns a response with status code 200 for successful updation in the table.")
    @PostMapping(value="/job/update",consumes="application/json", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response updateJob(@RequestHeader("Authorization") String bearerToken,@RequestBody Job job){
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                if(role.equalsIgnoreCase("admin")) {
                    response = adminServices.updateJob(job);
                }
                else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");

                }
            }else throw new Exception("bearer token is invalid");
        }
        catch (Exception e){
            response.setStatusCode(HttpStatus.FORBIDDEN.value());
            response.setStatusMessage("Exception occurred in updateJob controller : "+e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "Add questions for a job posting in the questionnaire table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value="/questionnaire",consumes="application/json", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createQuestionnaire(@RequestHeader("Authorization") String bearerToken,@RequestBody Questionnaire questionnaire){
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                log.info(role);
                if(role.equalsIgnoreCase("Admin")) {
                    response = adminServices.createQuestionnaire(questionnaire);
                }else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in createQuestionnaire controller : "+e.getMessage());
        }
        return response;
    }



    @ApiOperation(value = "Fetch filtered job from the job table", notes = "Returns a response with status code 200 for successful fetch from the job table.")
    @GetMapping(value = "/jobs/", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getJob(@RequestHeader("Authorization") String bearerToken, JobFilter jobFilter, @RequestParam(value="pageNumber", defaultValue="0",required=false) Integer pageNumber) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email=idToken.getPayload().getEmail();
                String role= userController.getRole(email);
                int pageSize = 24;
                Pageable p = PageRequest.of(pageNumber, pageSize);
                log.info(role);
                if(role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")){
                    Page<Job> jobList = customJobRepository.fetchAll(jobFilter, p);
                    if (jobList.getSize() != 0) {
                        log.info("JobFilter is {} :" + jobFilter);
                        log.info("The job fetched is {}", jobList);
                        response.setStatusCode(HttpStatus.OK.value());
                        response.setStatusMessage("Successfully received the job details");
                        response.setData(jobList);
                        response.setPages(jobList.getTotalPages());
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else {
                        log.info("No job was found for the corresponding job ID");
                        response.setStatusCode(HttpStatus.NOT_FOUND.value());
                        response.setStatusMessage("No job was found for the corresponding job ID");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                }else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            }else throw new Exception("bearer token is invalid");
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting job details " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}


    @ApiOperation(value = "Add job application in the JobApplication table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @RequestMapping(path = "/jobApplication/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @SecurityRequirement(name = "Bearer Authentication")
    Response createJobApplication(@RequestHeader("Authorization") String bearerToken,@RequestPart("jobApplication") String jobApplication, @RequestPart("files") MultipartFile[] files) {

        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("student")) {
//
                    log.info("The job application is : {}", jobApplication);
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    JobApplication jobApplication1 = objectMapper.readValue(jobApplication, JobApplication.class);
                    response = adminServices.createJobApplication(jobApplication1, files);
                } else {
                    log.error("You need admin or student role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Access is missing, please contact the vigyanshaala team");
                }
            }else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while adding job application ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding job application " + e);
        }
        return response;
    }


    @RequestMapping(path = "/studentDocument/", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseBody
    public Response createStudentDocument(@RequestHeader("Authorization") String bearerToken,@RequestPart("jobApplication") String jobApplication, @RequestPart("file") MultipartFile file) throws IOException {
        StudentDocument studentDocument = new StudentDocument();
        Response response = new Response<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    JobApplication jobApplication1 = objectMapper.readValue(jobApplication, JobApplication.class);
                    studentDocument.setJobApplication(jobApplication1);
                    studentDocument.setBlobData(file.getBytes());
                    adminServices.createStudentDocument(jobApplication1, file);
                }else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred in createStudentDocument ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred in createStudentDocument " + e);
        }
        return response;
    }

    @ApiOperation(value = "Add document type in the DocumentType table", notes = "Returns a response with status code 200 for successful addition in the table.")
    @PostMapping(value = "/documentType", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createDocumentType(@RequestHeader("Authorization") String bearerToken,@RequestBody DocumentType documentType) {
        Response response = new Response();
        try {
            GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    log.info("The document type is : {}", documentType);
                    response = adminServices.createDocumentType(documentType);
                } else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            } else throw new Exception("bearer token is invalid");
        }catch (Exception e) {
            log.error("Exception occurred while adding document type ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding document type " + e);
        }
        return response;
    }

    @ApiOperation(value = "Get job application list from the jobApplication table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all job applications.")
    @GetMapping(value = "/jobApplication/all", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getJobApplicationList(@RequestHeader("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try {
            GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    responseEntity = adminServices.getJobApplicationList();
                } else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while getting job application list ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting job application list" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get document type list from the documentType table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all document types.")
    @GetMapping(value = "/documentType/all", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getDocumentTypeList(@RequestHeader("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response = new Response();
                try {
                    GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
                    if (Objects.nonNull(idToken)) {
                        String email = idToken.getPayload().getEmail();
                        String role = userController.getRole(email);
                        log.info(role);
                        if (role.equalsIgnoreCase("Admin")) {
                            responseEntity = adminServices.getDocumentTypeList();
                        } else {
                            log.error("You need admin role to perform this action");
                            response.setStatusCode(HttpStatus.FORBIDDEN.value());
                            response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                        }
                    } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while getting document type list ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting document type list" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }


    @ApiOperation(value = "Get student document list from the studentDocument table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all student documents.")
    @GetMapping(value = "/studentDocument/all", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getStudentDocumentList(@RequestHeader("Authorization") String bearerToken) {
        ResponseEntity responseEntity;
        Response response = new Response();
        try {
            GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    responseEntity = adminServices.getStudentDocumentList();
                } else {
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                }
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while getting student document list ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting student document list" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/upload")
    public void uploadFile(@RequestHeader("Authorization") String bearerToken,@RequestParam("file") MultipartFile file) throws IOException {
        try {
            GoogleIdToken idToken = entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                String email = idToken.getPayload().getEmail();
                String role = userController.getRole(email);
                log.info(role);
                if (role.equalsIgnoreCase("Admin")) {
                    adminServices.uploadFile(file);
                } else {
                    log.error("You need admin role to perform this action");
                }
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred in upload File", e);
        }
    }

}