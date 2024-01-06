//package com.ffg.Vigyanshaala.controller;
//import com.ffg.Vigyanshaala.controller.JobPortalController.AdminController;
//import com.ffg.Vigyanshaala.model.JobPortal.JobDetails;
//import com.ffg.Vigyanshaala.response.Response;
//import com.ffg.Vigyanshaala.service.JobPortalService.AdminServices;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import java.time.format.DateTimeFormatter;
//import java.time.LocalDateTime;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//import java.util.Date;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ExtendWith(MockitoExtension.class)
//public class AdminControllerTest {
//
//    @InjectMocks
//    AdminController jobPortalController;
//    @Mock
//    AdminServices adminServices;
//
//    @Before
//    public void setup()
//    {
//        MockitoAnnotations.initMocks(this);
//    }
//
//
//    public JobDetails createNewJob()
//    {
//        JobDetails jobDetails=new JobDetails();
//        jobDetails.setCompanyName("JP Morgan Chase & Co.");
//        jobDetails.setJobTitle("Software Development Engineer 1");
//        jobDetails.setJobDescription("Needs to have hands on experience in Java");
//        jobDetails.setSalary("Rs. 10,00,000 per annum");
//        jobDetails.setJobLocation("Bengaluru");
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        Date postingDate=new Date(dtf.format(now));
//        System.out.println(postingDate);
//        jobDetails.setPostingDate(postingDate);
//
//        LocalDateTime expiry=LocalDateTime.now().plusDays( 30 );
//        Date expiryDate=new Date(dtf.format(expiry));
//        System.out.println(expiryDate);
//        jobDetails.setExpiryDate(expiryDate);
//
//
//        jobDetails.setQuestion1("Please provide class 10th percentage");
//        jobDetails.setQuestion2("Please provide class 12th percentage");
//
//
//        return jobDetails;
//    }
////    @Test
////    public void createJobTest()
////    {
////
////        JobDetails jobDetails=createNewJob();
////        Response response=new Response();
////        response.setStatusCode(200);
////        response.setStatusMessage("Successfully stored Job");
////
////        Mockito.when(adminServices.createJobImpl(jobDetails)).thenReturn(response);
////        Response response1=jobPortalController.createJob(jobDetails);
////        assertEquals(200,response1.getStatusCode());
////
////        Mockito.when(adminServices.createJobImpl(jobDetails)).thenThrow(RuntimeException.class);
////        Response response2=jobPortalController.createJob(jobDetails);
////        assertEquals(500,response2.getStatusCode());
////        return;
////
////
////    }
//
//}
