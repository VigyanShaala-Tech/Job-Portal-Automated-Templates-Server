package com.vigyanshaala.serviceImpl.jobPortalServiceImpl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vigyanshaala.entity.jobPortalEntity.EmailDetails;
import com.vigyanshaala.entity.jobPortalEntity.Job;
import com.vigyanshaala.entity.jobPortalEntity.JobApplication;
import com.vigyanshaala.entity.jobPortalEntity.StudentDocument;
import com.vigyanshaala.repository.jobPortalRepository.ExpiredJobsRepository;
import com.vigyanshaala.repository.jobPortalRepository.JobApplicationRepository;
import com.vigyanshaala.repository.jobPortalRepository.JobRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.SystemServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@Transactional
public class SystemServiceImpl implements SystemServices {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${serverCodePath}")
    private String serverCodePath;
    @Value("${jobApplicationsZipPath}")
    private String jobApplicationsZipPath;
    @Value("${emailDuration}")
    public String emailDuration;
    @Value("${expiredJob}")
    public String expiredJob;
    @Value("${fromEmail}")
    private String sender;

    private final ExpiredJobsRepository expiredJobsRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final JobRepository jobRepository;

    public SystemServiceImpl(ExpiredJobsRepository expiredJobsRepository, JobApplicationRepository jobApplicationRepository, JobRepository jobRepository) {
        this.expiredJobsRepository = expiredJobsRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    @Scheduled(cron = "${expiredJob}")
    public ResponseEntity deleteExpiredJobs() {
//        ArrayList<Job> results = null;
        Response response = new Response();
        try {
              SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
              Date date = new Date();
              expiredJobsRepository.softdeleteJobs(formatter.format(date));
//            System.out.println(results);
            log.info("Successfully soft deleted expired jobs");
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Successfully flagged expired jobs");
        } catch (Exception e) {
            log.error("Exception occurred while soft deleting titles ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while soft deleting titles " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
// working with earlier approach
//    @Override
//    public ResponseEntity mailJobApplicationsToHr(){
////        ArrayList<Job> results = null;
//        Response response = new Response();
//        List<String> hrEmailList = new ArrayList<>();
//        List<String> jobIdList = new ArrayList<>();
//        List<JobApplication> jobApplications = new ArrayList<>();
//        Map<String, List<JobApplication>> hrToJobApplicationMap = new HashMap<>();
//        //List<JobApplication> jobApplicationsCorrespondingToHr = new ArrayList<>();
//        try {
//            List<JobApplication> jobApplicationList = jobApplicationRepository.findByisJobApplicationPostedToHr(false);
//            System.out.println(jobApplicationList);
//            log.info("Job application list yet to be posted to HR is" + jobApplicationList);
//            //log.info("Successfully soft deleted expired jobs");
//            for(JobApplication jobApplication: jobApplicationList) {
//                String hrEmail = jobApplication.getJob().getHrEmail();
//                String jobId = jobApplication.getJob().getJobId();
//                if(!hrEmailList.contains(hrEmail))
//                    hrEmailList.add(hrEmail);
//                if(!jobIdList.contains(jobId))
//                    jobIdList.add(jobId);
//
//            }
//
//            for(String s : hrEmailList) {
//                jobApplications = new ArrayList<>();
//                for (JobApplication jobApplication : jobApplicationList) {
//                    if (jobApplication.getJob().getHrEmail().equals(s))
//                        jobApplications.add(jobApplication);
//                }
//                hrToJobApplicationMap.put(s, jobApplications);
//            }
//
//            for(Map.Entry<String, List<JobApplication>> entry : hrToJobApplicationMap.entrySet()) {
//                if(entry.getValue().size() != 0)
//                    generate(entry.getValue(), entry.getKey());
//            }
//
//
//            System.out.println(hrEmailList);
//            log.info("HR Email list is " + hrEmailList);
//
////            for(String email : hrEmailList) {
////               // j
////            }
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setStatusMessage("Successfully flagged expired jobs");
//        }catch(Exception e)
//        {
//            log.error("Exception occurred while soft deleting titles ",e);
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Exception occurred while soft deleting titles "+e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }

    @Override
    public ResponseEntity mailJobApplicationsToHr() {

        Response response = new Response();
        List<String> hrEmailList = new ArrayList<>();
        List<String> jobIdList = new ArrayList<>();
        List<String> studentIDList = new ArrayList<>();
        List<String> studentIDs = new ArrayList<>();
        List<List<String>> separatedSIDList = new ArrayList<>();
        Map<String, List<String>> jobIdtoStudentIdMap = new HashMap<>();
        try {
            List<JobApplication> jobApplicationList = jobApplicationRepository.findByisJobApplicationPostedToHr(false);
            System.out.println(jobApplicationList);
            log.info("Job application list yet to be posted to HR is" + jobApplicationList);
            for (JobApplication jobApplication : jobApplicationList) {
                String hrEmail = jobApplication.getJob().getHrEmail();
                String jobId = jobApplication.getJob().getJobId();
                if (!hrEmailList.contains(hrEmail))
                    hrEmailList.add(hrEmail);
                if (!jobIdList.contains(jobId))
                    jobIdList.add(jobId);
                for (String jobId1 : jobIdList) {
                    if (jobApplication.getJob().getJobId().equals(jobId1))
                        studentIDList.add(jobApplication.getStudentId());
                }
            }

            int i = 1;
            for (String s : jobIdList) {
                studentIDs = new ArrayList<>();
                for (JobApplication jobApplication : jobApplicationList) {
                    if (jobApplication.getJob().getJobId().equals(s))
                        studentIDs.add(jobApplication.getStudentId());
                }
                if (studentIDs.size() > 4) {
                    separatedSIDList = separate(studentIDs, 4);
                    for (List<String> list : separatedSIDList) {
                        jobIdtoStudentIdMap.put(s + "-set" + String.valueOf(i), list);
                        i++;
                    }
                } else {
                    jobIdtoStudentIdMap.put(s, studentIDs);
                }

            }

            for (Map.Entry<String, List<String>> entry : jobIdtoStudentIdMap.entrySet()) {
                if (entry.getValue().size() != 0) {
                    generate(entry.getKey(), entry.getValue());
                }
            }
            System.out.println(hrEmailList);
            log.info("HR Email list is " + hrEmailList);

            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Successfully flagged expired jobs");
        } catch (Exception e) {
            log.error("Exception occurred while mailing job applications to HR ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while mailing job applications to HR" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    static <T> List<List<T>> separate(List<T> path, final int size) {
        List<List<T>> separated = new ArrayList<>();

        for (int i = 0; i < path.size(); i += size) {
            separated.add(new ArrayList<>(path.subList(i, Math.min(path.size(), i + size))));
        }
        return separated;
    }


    public void generate(String jobId, List<String> studentIds) throws DocumentException, IOException {
        String jobId1 = "";
        try {
            {
                {
                    if (jobId.contains("-set"))
                        jobId1 = jobId.split("-set")[0];
                    else
                        jobId1 = jobId;
                    Job job = jobRepository.findByJobId(jobId1);
                    File directory = new File(jobApplicationsZipPath + jobId);
                    if (!directory.exists()) {
                        directory.mkdir();
                    } else {
                        FileUtils.forceDelete(directory);
                        FileUtils.forceMkdir(directory);
                    }
                    if (studentIds != null) {
                        for (String s : studentIds) {
                            List<JobApplication> jobApplications = jobApplicationRepository.findBystudentId(s);
                            File studentFolder = new File(directory + "\\" + s);
                            if (!studentFolder.exists()) {
                                studentFolder.mkdir();
                            }
                            if (jobApplications != null) {
                                for (JobApplication j : jobApplications) {
                                    if (j.getJob().getJobId().equals(jobId1)) {
                                        Document document = new Document(PageSize.A4);
                                        String fileName = studentFolder + "\\" + "ApplicationResponse.pdf";
                                        //fileName = fileName.replaceAll("[-+.^:,]", "").concat(".pdf");
                                        String zipFileName = fileName.concat(".zip");
                                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
                                        writer.setCloseStream(false);
                                        // Opening the created document to modify it
                                        document.open();

                                        // Creating font
                                        // Setting font style and size
                                        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
                                        fontTitle.setSize(20);

                                        // Creating paragraph
                                        Paragraph paragraph = new Paragraph("Job application", fontTitle);

                                        // Aligning the paragraph in document
                                        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

                                        // Adding the created paragraph in document
                                        document.add(paragraph);

                                        // Creating a table of 3 columns
                                        PdfPTable table = new PdfPTable(2);

                                        // Setting width of table, its columns and spacing
                                        table.setWidthPercentage(100f);
                                        //table.setWidths(new int[] { 3, 3, 3 });
                                        table.setSpacingBefore(5);

                                        // Create Table Cells for table header
                                        PdfPCell cell = new PdfPCell();

                                        // Setting the background color and padding
                                        cell.setBackgroundColor(CMYKColor.DARK_GRAY);
                                        cell.setPadding(5);

                                        // Creating font
                                        // Setting font style and size
                                        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
                                        font.setColor(CMYKColor.WHITE);

                                        // Adding headings in the created table cell/ header
                                        // Adding Cell to table
                                        cell.setPhrase(new Phrase("Job ID", font));
                                        table.addCell(cell);
                                        table.addCell(String.valueOf(j.getJobApplicationId()));

                                        cell.setPhrase(new Phrase("Company", font));
                                        table.addCell(cell);
                                        table.addCell(String.valueOf(j.getJob().getCompany().getCompanyName()));

                                        cell.setPhrase(new Phrase("Job Title", font));
                                        table.addCell(cell);
                                        table.addCell(String.valueOf(j.getJob().getJobTitle().getJobTitle()));

                                        cell.setPhrase(new Phrase("Job Location", font));
                                        table.addCell(cell);
                                        table.addCell(String.valueOf(j.getJob().getJobLocation().getJobLocation()));

                                        cell.setPhrase(new Phrase("Job description", font));
                                        table.addCell(cell);
                                        table.addCell(String.valueOf(j.getJob().getJobDescription()));

                                        if (j.getJob().getQuestionnaire().getQuestion1() != "" || j.getJob().getQuestionnaire().getQuestion1() != null) {
                                            cell.setPhrase(new Phrase("Question 1", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getJob().getQuestionnaire().getQuestion1()));

                                            cell.setPhrase(new Phrase("Answer 1", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getAnswer1()));
                                        }

                                        if (j.getJob().getQuestionnaire().getQuestion2() != "" || j.getJob().getQuestionnaire().getQuestion2() != null) {
                                            cell.setPhrase(new Phrase("Question 2", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getJob().getQuestionnaire().getQuestion2()));

                                            cell.setPhrase(new Phrase("Answer 2", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getAnswer2()));
                                        }

                                        if (j.getJob().getQuestionnaire().getQuestion3() != "" || j.getJob().getQuestionnaire().getQuestion3() != null) {
                                            cell.setPhrase(new Phrase("Question 3", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getJob().getQuestionnaire().getQuestion3()));

                                            cell.setPhrase(new Phrase("Answer 3", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getAnswer3()));
                                        }

                                        if (j.getJob().getQuestionnaire().getQuestion4() != "" || j.getJob().getQuestionnaire().getQuestion4() != null) {
                                            cell.setPhrase(new Phrase("Question 4", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getJob().getQuestionnaire().getQuestion4()));

                                            cell.setPhrase(new Phrase("Answer 4", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getAnswer4()));
                                        }

                                        if (j.getJob().getQuestionnaire().getQuestion5() != "" || j.getJob().getQuestionnaire().getQuestion5() != null) {
                                            cell.setPhrase(new Phrase("Question 5", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getJob().getQuestionnaire().getQuestion5()));

                                            cell.setPhrase(new Phrase("Answer 5", font));
                                            table.addCell(cell);
                                            table.addCell(String.valueOf(j.getAnswer5()));
                                        }
                                        j.setIsJobApplicationPostedToHr(Boolean.TRUE);
                                        jobApplicationRepository.save(j);
                                        table.addCell(new Phrase());
                                        table.addCell(new Phrase());


                                        // Adding the created table to document
                                        document.add(table);

                                        // Closing the document
                                        document.close();

                                        List<StudentDocument> studentDocumentList = j.getStudentDocumentList();
                                        for (StudentDocument studentDocument : studentDocumentList) {
                                            File fileStructure = new File(studentFolder + "\\" + studentDocument.getFileName() + "." + studentDocument.getFileExtension());
                                            if (!fileStructure.exists()) {
                                                fileStructure.createNewFile();

                                                OutputStream out = new FileOutputStream(fileStructure);

                                                out.write(studentDocument.getBlobData());
                                                out.close();
                                            }
                                        }

                                    }
                                }

                            }
                            // Zipping folders
                            //zip("C:\\Users\\harin\\IdeaProjects\\vigyanshaala-server-new\\" + jobId, "C:\\Users\\harin\\IdeaProjects\\vigyanshaala-server-new\\JobApplications\\" + jobId + ".zip");

                            //sendMailWithAttachment(details);
                        }
                        zip(jobApplicationsZipPath + jobId, jobApplicationsZipPath + jobId + ".zip");
                        EmailDetails details = new EmailDetails();
                        details.setSubject("Details of job applications");
                        details.setRecipient(job.getHrEmail());
                        details.setMsgBody("Hi, PFA responses to the list of responses for the job applications you have posted. Best regards, Team VigyanShaala ");
                        //details.setAttachment("C:\\Users\\harin\\IdeaProjects\\vigyanshaala-server-new\\JobApplications\\" + jobId + ".zip");
                        details.setAttachment(jobApplicationsZipPath + jobId + ".zip");
                        sendMailWithAttachment(details);

                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.format("The file does not exist");
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
            System.err.println("I/O error: " + ex);
        }
    }

    public static void zip(final String sourcNoteseDirPath, final String zipFilePath) throws IOException {
        Path p1 = Path.of(zipFilePath);
        // Delete zip if it already exists
        if (Files.exists(p1))
            Files.delete(p1);
        Path zipFile = Files.createFile(Paths.get(zipFilePath));

        Path sourceDirPath = Paths.get(sourcNoteseDirPath);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
             Stream<Path> paths = Files.walk(sourceDirPath)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }


    public String sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be sent
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    @Scheduled(cron = "${emailDuration}")
    public void batchMailJobApplicationToHR() {
        // some logic that will be executed on a schedule
        log.info("Mailing job applications to HR...");
        try {
            mailJobApplicationsToHr();
            deleteDirectory(new File(jobApplicationsZipPath));

        } catch (Exception e) {
            log.error("Exception occurred while mailing job applications to HR " + e);
        }
    }
    
    public ResponseEntity deleteDirectory(File directoryToBeDeleted) {
        Response response = new Response();
        try {
            FileUtils.cleanDirectory(directoryToBeDeleted);
            log.info("Successfully deleted directory");
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Successfully deleted directory");
        } catch (Exception e) {
            log.error("Exception occurred while deleting directory ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while deleting directory " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
