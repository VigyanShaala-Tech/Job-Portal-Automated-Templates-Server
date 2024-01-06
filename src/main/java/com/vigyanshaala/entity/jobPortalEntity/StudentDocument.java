package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_document")
public class StudentDocument {
    public String getStudentDocumentId() {
        return studentDocumentId;
    }

    public void setStudentDocumentId(String studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData;
    }

    public StudentDocument(String studentDocumentId, JobApplication jobApplication, String documentType, byte[] blobData, String fileName, String fileExtension) {
        this.studentDocumentId = studentDocumentId;
        this.jobApplication = jobApplication;
        this.documentType = documentType;
        this.blobData = blobData;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public StudentDocument() {
    }

    @Id
    private String studentDocumentId;
    @ManyToOne(cascade = CascadeType.MERGE)
    private JobApplication jobApplication;
    private String documentType;
    @Lob
    private byte[] blobData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    private String fileExtension;
}
