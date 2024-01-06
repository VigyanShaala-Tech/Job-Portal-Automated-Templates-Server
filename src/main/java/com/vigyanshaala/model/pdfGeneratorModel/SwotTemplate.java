package com.vigyanshaala.model.pdfGeneratorModel;

public class SwotTemplate {
    private String strength;
    private String weakness;
    private String opportunity;
    private String threat;
    private  String studentEmail;
    private String studentName;
    private String studentDegree;
    private String goal;

    public SwotTemplate(){}
    SwotTemplate(String studentName,String strength,String weakness, String opportunity, String threat, String studentDegree, String goal){

        this.studentName=studentName;
        this.strength=strength;
        this.weakness=weakness;
        this.opportunity=opportunity;
        this.threat=threat;
        this.studentEmail=studentName;
        this.studentDegree=studentDegree;
        this.goal=goal;

    }
    public String getStrength()
    {
        return strength;
    }

    public void setStrength(String strength){
        this.strength=strength;
    }

    public String getWeakness(){return weakness;}
    public void setWeakness(String weakness){this.weakness=weakness;}
    public String getThreat(){return threat;}
    public void setThreat(String threat){this.threat=threat;}
    public String getOpportunity(){return opportunity;}
    public void setOpportunity(String opportunity){this.opportunity=opportunity;}
    public void setStudentEmail(String studentEmail){this.studentEmail=studentEmail;}
    public String getStudentEmail(){return studentEmail;}
    public void setStudentDegree(String studentDegree){this.studentDegree=studentDegree;}
    public String getStudentDegree(){return studentDegree;}
    public void setGoal(String goal){this.goal=goal;}
    public String getGoal(){return goal;}
    public String getStudentName(){return studentName;}
    public void setStudentName(String studentName){this.studentName=studentName;}
}

