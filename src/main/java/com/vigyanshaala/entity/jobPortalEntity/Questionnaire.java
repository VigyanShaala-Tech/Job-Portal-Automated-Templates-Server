package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*This is the company table that stores all the company details which the admin has submitted */
@Entity
@Table(name="questionnaire")

public class Questionnaire {

    private @Id
    String questionnaireId;

    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;


    public Questionnaire() {
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public Questionnaire(String questionnaireId, Job job, String question1, String question2, String question3, String question4, String question5) {
        this.questionnaireId = questionnaireId;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }



    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }




}
