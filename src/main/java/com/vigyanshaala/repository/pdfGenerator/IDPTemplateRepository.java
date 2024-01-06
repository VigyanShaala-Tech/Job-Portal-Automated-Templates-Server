package com.vigyanshaala.repository.pdfGenerator;

import com.vigyanshaala.entity.pdfGeneratorEntity.IDPTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDPTemplateRepository extends JpaRepository<IDPTemplateEntity, String> {
    @Transactional
    @Query(value = "SELECT * FROM idp_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    IDPTemplateEntity getLatestVersion(
            @Param("studentEmail") String studentEmail);

    @Transactional
    @Query(value = "SELECT * from idp_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)
    List<IDPTemplateEntity> getTemplate(
            @Param("studentEmail") String studentEmail, @Param("version") Long version);
}
