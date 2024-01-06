package com.vigyanshaala.service.jobPortalService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigyanshaala.entity.jobPortalEntity.*;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*Service interface which declares all the functions used by the admin */
public interface AdminServices {
    Response createJob(Job job);
    Response addCompany(Company company);
    Response addJobLocation(JobLocation jobLocation);
    Response addJobTitle(JobTitle jobTitle);
    Response addWorkmode(WorkMode workmode);
    Response addIndustry(Industry industry);
    Response addEducationLevel(EducationLevel educationLevel);
    ResponseEntity getCompanyList();
    ResponseEntity getJobLocationList();
    ResponseEntity getJobTitleList();
    ResponseEntity getWorkmodeList();
    ResponseEntity getIndustryList();
    ResponseEntity getEducationLevelList();
    Response createQuestionnaire(Questionnaire questionnaire);
    Response updateJob(Job job);
    //ResponseEntity getJobById(String jobId);
    Response createJobApplication(JobApplication jobApplication, MultipartFile[] files) throws IOException;

    Response createStudentDocument(JobApplication jobApplication, MultipartFile file) throws IOException;

    Response createDocumentType(DocumentType documentType);

    ResponseEntity getJobApplicationList();

    ResponseEntity getDocumentTypeList();

    ResponseEntity getStudentDocumentList();

    StudentDocument uploadFile(MultipartFile file) throws IOException;


    public default JobApplication getJson(String jobApplication, MultipartFile file) {

        JobApplication jobApplication1 = new JobApplication();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jobApplication1 = objectMapper.readValue(jobApplication, JobApplication.class);
        } catch (IOException err) {
            System.out.printf("Error", err.toString());
        }

        //int fileCount = file.size();
        //userJson.setCount(fileCount);

        return jobApplication1;

    }
}