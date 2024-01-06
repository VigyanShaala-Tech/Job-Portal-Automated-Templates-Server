package com.vigyanshaala.entity.pdfGeneratorEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="critical_thinking_template")
@Data
public class CriticalThinkingEntity {
    @Id
    private  String studentId;
    private  String studentEmail;
    private  Long version;
    @Lob
    private  String answerA;
    @Lob
    private  String answerB;
    @Lob
    private  String answerC;

    public CriticalThinkingEntity(){
    }
}
