package com.vigyanshaala.model.pdfGeneratorModel;

public class SmartGoalsTemplate {

    private String goal;
    private String specific;
    private String measurable;
    private String achievable;
    private String relevant;
    private String timeBound;
    private  String studentEmail;


    public SmartGoalsTemplate() {}

    public SmartGoalsTemplate(String specific, String measurable, String achievable, String relevant, String timeBound, String goal, String studentEmail){

        this.specific=specific;
        this.measurable=measurable;
        this.achievable=achievable;
        this.relevant=relevant;
        this.timeBound=timeBound;
        this.goal=goal;
        this.studentEmail=studentEmail;

    }

    public String getSpecific()
    {
        return specific;
    }

    public void setSpecific(String specific){
        this.specific=specific;
    }

    public String getMeasurable(){return measurable;}
    public void setMeasurable(String measurable){this.measurable=measurable;}

    public String getAchievable(){return achievable;}
    public void setAchievable(String achievable){this.achievable=achievable;}

    public String getRelevant(){return relevant;}
    public void setRelevant(String relevant){this.relevant=relevant;}

    public String getTimeBound(){return timeBound;}
    public void setTimeBound(String timeBound){this.timeBound=timeBound;}


    public void setGoal(String goal){this.goal=goal;}
    public String getGoal(){return goal;}

    public void setStudentEmail(String studentEmail){this.studentEmail=studentEmail;}
    public String getStudentEmail(){return studentEmail;}


}

