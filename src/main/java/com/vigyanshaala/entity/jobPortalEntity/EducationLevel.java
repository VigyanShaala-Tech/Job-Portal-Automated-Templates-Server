package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="education_level")
@Data
public class EducationLevel {
    @Id
    private  String educationLevelId;
    private  String educationLevel;
    public EducationLevel(){}
    public EducationLevel(String educationLevelId,String educationLevel)
    {
        this.educationLevelId=educationLevelId;
        this.educationLevel=educationLevel;
    }
}
