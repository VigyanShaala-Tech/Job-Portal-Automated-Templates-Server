package com.vigyanshaala.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user_role")
@Data
public class UserRole {

    private String name;
    private  String role;
    private String cohort;
    private String completionStatus;
    @Id
    private  String emailId;


    public UserRole(){}

}
