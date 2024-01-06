package com.vigyanshaala.repository.pdfGenerator;

import com.vigyanshaala.entity.pdfGeneratorEntity.CollaborationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollaborationRepository extends JpaRepository<CollaborationEntity, String> {
    @Transactional
    @Query(value="SELECT * FROM collaboration_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    CollaborationEntity getLatestVersion (
            @Param("studentEmail") String studentEmail);

    @Transactional
    @Query(value="SELECT * from collaboration_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)
    List<CollaborationEntity> getCollaborationTemplate (
            @Param("studentEmail") String studentEmail,@Param("version") Long version);


}
