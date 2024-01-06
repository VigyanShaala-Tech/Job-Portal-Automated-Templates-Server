
package com.vigyanshaala.repository.pdfGenerator;

import com.vigyanshaala.entity.pdfGeneratorEntity.CreativeMindsetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CreativeMindsetRepository extends JpaRepository<CreativeMindsetEntity, String> {
    @Transactional
    @Query(value="SELECT * FROM creativemindset_template a where a.student_email=:studentEmail order by version desc limit 1", nativeQuery = true)
    CreativeMindsetEntity getCreativeMindsetLatestVersion (
            @Param("studentEmail") String studentEmail);

    @Transactional
    @Query(value="SELECT * from creativemindset_template a where a.student_email=:studentEmail and a.version=:version", nativeQuery = true)
    List<CreativeMindsetEntity> getCreativeMindsetTemplate (
            @Param("studentEmail") String studentEmail,@Param("version") Long version);


}
