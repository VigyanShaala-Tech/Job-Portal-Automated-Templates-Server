package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="industry")
@Data

public class Industry {
    @Id
    private  String industryId;
    private  String industry;
    public Industry(){}
    public Industry(String industryId,String industry)
    {
        this.industryId=industryId;
        this.industry=industry;
    }
}
