package com.vigyanshaala.entity.pdfGeneratorEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

/**This is the job_title table which stores all the job titles that the admin has submitted*/
@Entity
@Table(name="swot_template")
@Data
public class SwotTemplateEntity {

    @Id
    private  String studentId;
    private  String studentEmail;
    private  Long version;
    @Lob
    private  String strength;
    @Lob
    private  String weakness;
    @Lob
    private  String opportunity;
    @Lob
    private  String threat;

    private  String studentName;
    private  String studentDegree;

    @Lob
    private  String goal;

    public SwotTemplateEntity(){
    }
}