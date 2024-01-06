package com.vigyanshaala.entity.pdfGeneratorEntity; //package name where the SmartGoalsEntity class belongs.

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;  // Data is an annotation that automatically generates boilerplate code for getters, setters

@Entity
@Table(name="smart_goals_template") // What should be the name here?
@Data
public class SmartGoalsTemplateEntity {

    @Id
    private  String studentId;       // private key will be Student id or goal id
    private  String studentEmail;    // do I need the student name variable like in SwotTemplateEntity as well?
    private  Long version;
    @Lob
    private  String goal;
    @Lob
    private  String specific;
    @Lob
    private  String measurable;
    @Lob
    private  String achievable;
    @Lob
    private  String relevant;
    @Lob
    private  String timeBound;


    public SmartGoalsTemplateEntity(){
    }

}