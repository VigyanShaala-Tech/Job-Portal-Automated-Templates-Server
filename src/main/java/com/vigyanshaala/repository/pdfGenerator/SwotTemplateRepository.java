package com.vigyanshaala.repository.pdfGenerator;
import com.vigyanshaala.entity.pdfGeneratorEntity.SwotTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SwotTemplateRepository extends JpaRepository<SwotTemplateEntity,String> {

    @Transactional
    @Query(value="SELECT * FROM swot_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    SwotTemplateEntity getLatestVersion (
            @Param("studentEmail") String studentEmail);

    @Transactional
    @Query(value="SELECT * from swot_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)
    List<SwotTemplateEntity> getTemplate (
            @Param("studentEmail") String studentEmail,@Param("version") Long version);



}

