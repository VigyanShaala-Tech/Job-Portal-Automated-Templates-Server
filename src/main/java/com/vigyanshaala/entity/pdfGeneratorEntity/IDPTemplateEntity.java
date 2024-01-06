package com.vigyanshaala.entity.pdfGeneratorEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "idp_template")
@Data
public class IDPTemplateEntity {
    @Id
    private String studentId;
    private String studentEmail;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Lob
    private String studentName;
    @Lob
    private Long version;
    @Lob
    private String goalagoal1;
    @Lob
    private String goalaname;
    @Lob
    private String goalaeducation1;
    @Lob
    private String goalaeducation2;
    @Lob
    private String goalaproudMoments;
    @Lob
    private String goalaraisec;
    @Lob
    private String goalamatchStrength;
    @Lob
    private String goaladecreaseThreat;
    @Lob
    private String goalaaquiredHardSkills;
    @Lob
    private String goalaaquiredSoftSkills;

    @Lob
    private String goalagoal2;
    @Lob
    private String goalacareerOptions;
    @Lob
    private String goalahardSkillsToAquire;
    @Lob
    private String goalasoftSkillsToAquire;
    @Lob
    private String goalaemployers;
    @Lob
    private String goalamentor1;
    @Lob
    private String goalamentor2;
    @Lob
    private String goalamentor3;
    @Lob
    private String goalamentor4;
    @Lob
    private String goalamileStone1;
    @Lob
    private String goalamileStone2;
    @Lob
    private String goalamileStone3;
    @Lob
    private String goalam1Step1;
    @Lob
    private String goalam1Step2;
    @Lob
    private String goalam1Step3;
    @Lob
    private String goalam2Step1;
    @Lob
    private String goalam2Step2;
    @Lob
    private String goalam2Step3;
    @Lob
    private String goalam3Step1;
    @Lob
    private String goalam3Step2;
    @Lob
    private String goalam3Step3;
    @Lob
    private String goalaadjustment;
    @Lob
    private String goalareview;
    @Lob
    private String goalbgoal1;
    @Lob
    private String goalbname;
    @Lob
    private String goalbeducation1;
    @Lob
    private String goalbeducation2;
    @Lob
    private String goalbproudMoments;
    @Lob
    private String goalbraisec;
    @Lob
    private String goalbmatchStrength;
    @Lob
    private String goalbdecreaseThreat;
    @Lob
    private String goalbaquiredHardSkills;
    @Lob
    private String goalbaquiredSoftSkills;

    @Lob
    private String goalbgoal2;
    @Lob
    private String goalbcareerOptions;
    @Lob
    private String goalbhardSkillsToAquire;
    @Lob
    private String goalbsoftSkillsToAquire;
    @Lob
    private String goalbemployers;
    @Lob
    private String goalbmentor1;
    @Lob
    private String goalbmentor2;
    @Lob
    private String goalbmentor3;
    @Lob
    private String goalbmentor4;
    @Lob
    private String goalbmileStone1;
    @Lob
    private String goalbmileStone2;
    @Lob
    private String goalbmileStone3;
    @Lob
    private String goalbm1Step1;
    @Lob
    private String goalbm1Step2;
    @Lob
    private String goalbm1Step3;
    @Lob
    private String goalbm2Step1;
    @Lob
    private String goalbm2Step2;
    @Lob
    private String goalbm2Step3;
    @Lob
    private String goalbm3Step1;
    @Lob
    private String goalbm3Step2;
    @Lob
    private String goalbm3Step3;

    public IDPTemplateEntity() {
    }

    private String goalbadjustment;
    private String goalbreview;
}
