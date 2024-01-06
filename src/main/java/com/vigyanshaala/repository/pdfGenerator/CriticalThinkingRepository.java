package com.vigyanshaala.repository.pdfGenerator;

import com.vigyanshaala.entity.pdfGeneratorEntity.CriticalThinkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CriticalThinkingRepository extends JpaRepository<CriticalThinkingEntity, String> {
    @Transactional
    @Query(value="SELECT * FROM critical_thinking_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    CriticalThinkingEntity getLatestVersion (
            @Param("studentEmail") String studentEmail);

    @Transactional
    @Query(value="SELECT * from critical_thinking_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)
    List<CriticalThinkingEntity> getTemplate (
            @Param("studentEmail") String studentEmail,@Param("version") Long version);
}
