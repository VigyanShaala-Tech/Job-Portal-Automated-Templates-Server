package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_application")
public class JobApplication {
    public String getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(String jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setIsJobApplicationPostedToHr(boolean isJobApplicationPostedToHr) {
        this.isJobApplicationPostedToHr = isJobApplicationPostedToHr;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }


    public JobApplication(String jobApplicationId, String studentId, String answer1, String answer2, String answer3, String answer4, String answer5, boolean isJobApplicationPostedToHr, List<StudentDocument> studentDocumentList, String studentName, String studentEmail) {
        this.jobApplicationId = jobApplicationId;
        this.studentId = studentId;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.isJobApplicationPostedToHr = isJobApplicationPostedToHr;
        this.studentDocumentList = studentDocumentList;
        this.studentName = studentName;
        this.studentEmail= studentEmail;
    }

    public JobApplication() {
    }

    @Id
    private String jobApplicationId;
    private String studentId;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Job job;

    private String jobId;
    @Lob
    private String answer1;
    @Lob
    private String answer2;
    @Lob
    private String answer3;
    @Lob
    private String answer4;
    @Lob
    private String answer5;
    private boolean isJobApplicationPostedToHr;

    private String studentName;
    private String studentEmail;

    public List<StudentDocument> getStudentDocumentList() {
        return studentDocumentList;
    }

    public void setStudentDocumentList(List<StudentDocument> studentDocumentList) {
        this.studentDocumentList = studentDocumentList;
    }

    @OneToMany(cascade = CascadeType.MERGE)
    private List<StudentDocument> studentDocumentList;

    public boolean getIsJobApplicationPostedToHr() {
        return isJobApplicationPostedToHr;
    }
}