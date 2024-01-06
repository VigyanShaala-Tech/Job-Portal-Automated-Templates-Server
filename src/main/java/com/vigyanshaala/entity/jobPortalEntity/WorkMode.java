package com.vigyanshaala.entity.jobPortalEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="work_mode")
@Data

public class WorkMode {
    @Id
    private  String workModeId;
    private  String workMode;
    public WorkMode(){}
    public WorkMode(String workModeId,String workMode)
    {
        this.workModeId=workModeId;
        this.workMode=workMode;
    }
}
