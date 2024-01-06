package com.vigyanshaala.repository.pdfGenerator;

import com.vigyanshaala.entity.pdfGeneratorEntity.SmartGoalsTemplateEntity; // // specified as the entity type for the repository.
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository is provided by Spring Data JPA and offers generic CRUD (Create, Read, Update, Delete) operations for entities.
import org.springframework.data.jpa.repository.Query; // The @Query annotation allows for custom queries to be defined for the repository method.
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SmartGoalsRepository provides a Repository for CRUD Operations in Smart_Goals table
 */

public interface SmartGoalsTemplateRepository extends JpaRepository<SmartGoalsTemplateEntity,String> { // String : indicates the data type of the entity's primary key.

    // Returns a SmartGoalsTemplateEntity, representing the latest version of the SmartGoals template for specific student email.
    @Transactional
    @Query(value="SELECT * FROM smart_goals_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    SmartGoalsTemplateEntity getLatestVersion (
            @Param("studentEmail") String studentEmail);   //  @Param allows to dynamically pass values to the query based on the method arguments.It binds the method parameter studentEmail to the named parameter :studentEmail in the query.

    // retrieves a specific version of the SmartGoals template for a given student email.
    @Transactional
    @Query(value="SELECT * from smart_goals_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)  // :studentEmail and :version. These named parameters act as placeholders in the query, which will be replaced with actual values when the query is executed.
    List<SmartGoalsTemplateEntity> getTemplate (
            @Param("studentEmail") String studentEmail,@Param("version") Long version);



}

