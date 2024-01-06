package com.vigyanshaala.entity.pdfGeneratorEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ria_template")
@Data
public class RiasecEntity {

    @Id
    private  String studentId;
    private  String studentEmail;
    private  Long version;
    private  Long realistic;
    private  Long investigative;
    private  Long artistic;
    private  Long social;
    private  Long enterprising;
    private  Long conventional;
    private  String hollandCode;

    public RiasecEntity(){
    }
}
