package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.IDPTemplateEntity;
import com.vigyanshaala.model.pdfGeneratorModel.IDPTemplate;
import com.vigyanshaala.repository.pdfGenerator.IDPTemplateRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.IDPTemplateServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j

public class IDPTemplateServiceImpl implements IDPTemplateServices {
    private final IDPTemplateRepository idpTemplateRepository;

    public IDPTemplateServiceImpl(IDPTemplateRepository idpTemplateRepository) {
        this.idpTemplateRepository = idpTemplateRepository;

    }

    @Override
    public Response saveIDPTemplate(IDPTemplate idpTemplate) {

        Response response = new Response();
        try {
            IDPTemplateEntity idpTemplateEntity = new IDPTemplateEntity();
            IDPTemplateEntity idpTemplateEntityResult = idpTemplateRepository.getLatestVersion(idpTemplate.getStudentEmail());
            Long version = 0L;
            if (Objects.nonNull(idpTemplateEntityResult)) {
                version = idpTemplateEntityResult.getVersion();
                version = version + 1;
            }

            log.info("Version " + version);

            idpTemplateEntity.setStudentId(idpTemplate.getStudentEmail() + "_" + version);
            idpTemplateEntity.setStudentEmail(idpTemplate.getStudentEmail());
            idpTemplateEntity.setStudentName(idpTemplate.getStudentName());
            idpTemplateEntity.setVersion(version);

            idpTemplateEntity.setGoalaadjustment(idpTemplate.getGoalaadjustment());
            idpTemplateEntity.setGoalaaquiredSoftSkills(idpTemplate.getGoalaaquiredSoftSkills());
            idpTemplateEntity.setGoalaaquiredHardSkills(idpTemplate.getGoalaaquiredHardSkills());
            idpTemplateEntity.setGoalaemployers(idpTemplate.getGoalaemployers());
            idpTemplateEntity.setGoaladecreaseThreat(idpTemplate.getGoaladecreaseThreat());
            idpTemplateEntity.setGoalacareerOptions(idpTemplate.getGoalacareerOptions());
            idpTemplateEntity.setGoalaeducation1(idpTemplate.getGoalaeducation1());
            idpTemplateEntity.setGoalaeducation2(idpTemplate.getGoalaeducation2());
            idpTemplateEntity.setGoalagoal2(idpTemplate.getGoalagoal2());
            idpTemplateEntity.setGoalagoal1(idpTemplate.getGoalagoal1());
            idpTemplateEntity.setGoalam1Step1(idpTemplate.getGoalam1Step1());
            idpTemplateEntity.setGoalam1Step2(idpTemplate.getGoalam1Step2());
            idpTemplateEntity.setGoalam1Step3(idpTemplate.getGoalam1Step3());
            idpTemplateEntity.setGoalam2Step1(idpTemplate.getGoalam2Step1());
            idpTemplateEntity.setGoalam2Step2(idpTemplate.getGoalam2Step2());
            idpTemplateEntity.setGoalam2Step3(idpTemplate.getGoalam2Step3());
            idpTemplateEntity.setGoalam3Step1(idpTemplate.getGoalam3Step1());
            idpTemplateEntity.setGoalam3Step2(idpTemplate.getGoalam3Step2());
            idpTemplateEntity.setGoalam3Step3(idpTemplate.getGoalam3Step3());
            idpTemplateEntity.setGoalaproudMoments(idpTemplate.getGoalaproudMoments());
            idpTemplateEntity.setGoalaraisec(idpTemplate.getGoalaraisec());
            idpTemplateEntity.setGoalamatchStrength(idpTemplate.getGoalamatchStrength());
            idpTemplateEntity.setGoaladecreaseThreat(idpTemplate.getGoaladecreaseThreat());
            idpTemplateEntity.setGoalahardSkillsToAquire(idpTemplate.getGoalahardSkillsToAquire());
            idpTemplateEntity.setGoalasoftSkillsToAquire(idpTemplate.getGoalasoftSkillsToAquire());
            idpTemplateEntity.setGoalamentor1(idpTemplate.getGoalamentor1());
            idpTemplateEntity.setGoalamentor2(idpTemplate.getGoalamentor2());
            idpTemplateEntity.setGoalamentor3(idpTemplate.getGoalamentor3());
            idpTemplateEntity.setGoalamentor4(idpTemplate.getGoalamentor4());
            idpTemplateEntity.setGoalamileStone1(idpTemplate.getGoalamileStone1());
            idpTemplateEntity.setGoalamileStone2(idpTemplate.getGoalamileStone2());
            idpTemplateEntity.setGoalamileStone3(idpTemplate.getGoalamileStone3());
            idpTemplateEntity.setGoalareview(idpTemplate.getGoalareview());

            idpTemplateEntity.setGoalbadjustment(idpTemplate.getGoalbadjustment());
            idpTemplateEntity.setGoalbaquiredSoftSkills(idpTemplate.getGoalbaquiredSoftSkills());
            idpTemplateEntity.setGoalbaquiredHardSkills(idpTemplate.getGoalbaquiredHardSkills());
            idpTemplateEntity.setGoalbemployers(idpTemplate.getGoalbemployers());
            idpTemplateEntity.setGoalbdecreaseThreat(idpTemplate.getGoalbdecreaseThreat());
            idpTemplateEntity.setGoalbcareerOptions(idpTemplate.getGoalbcareerOptions());
            idpTemplateEntity.setGoalbeducation1(idpTemplate.getGoalbeducation1());
            idpTemplateEntity.setGoalbeducation2(idpTemplate.getGoalbeducation2());
            idpTemplateEntity.setGoalbgoal2(idpTemplate.getGoalbgoal2());
            idpTemplateEntity.setGoalbgoal1(idpTemplate.getGoalbgoal1());
            idpTemplateEntity.setGoalbm1Step1(idpTemplate.getGoalbm1Step1());
            idpTemplateEntity.setGoalbm1Step2(idpTemplate.getGoalbm1Step2());
            idpTemplateEntity.setGoalbm1Step3(idpTemplate.getGoalbm1Step3());
            idpTemplateEntity.setGoalbm2Step1(idpTemplate.getGoalbm2Step1());
            idpTemplateEntity.setGoalbm2Step2(idpTemplate.getGoalbm2Step2());
            idpTemplateEntity.setGoalbm2Step3(idpTemplate.getGoalbm2Step3());
            idpTemplateEntity.setGoalbm3Step1(idpTemplate.getGoalbm3Step1());
            idpTemplateEntity.setGoalbm3Step2(idpTemplate.getGoalbm3Step2());
            idpTemplateEntity.setGoalbm3Step3(idpTemplate.getGoalbm3Step3());
            idpTemplateEntity.setGoalbproudMoments(idpTemplate.getGoalbproudMoments());
            idpTemplateEntity.setGoalbraisec(idpTemplate.getGoalbraisec());
            idpTemplateEntity.setGoalbmatchStrength(idpTemplate.getGoalbmatchStrength());
            idpTemplateEntity.setGoalbdecreaseThreat(idpTemplate.getGoalbdecreaseThreat());
            idpTemplateEntity.setGoalbhardSkillsToAquire(idpTemplate.getGoalbhardSkillsToAquire());
            idpTemplateEntity.setGoalbsoftSkillsToAquire(idpTemplate.getGoalbsoftSkillsToAquire());
            idpTemplateEntity.setGoalbmentor1(idpTemplate.getGoalbmentor1());
            idpTemplateEntity.setGoalbmentor2(idpTemplate.getGoalbmentor2());
            idpTemplateEntity.setGoalbmentor3(idpTemplate.getGoalbmentor3());
            idpTemplateEntity.setGoalbmentor4(idpTemplate.getGoalbmentor4());
            idpTemplateEntity.setGoalbmileStone1(idpTemplate.getGoalbmileStone1());
            idpTemplateEntity.setGoalbmileStone2(idpTemplate.getGoalbmileStone2());
            idpTemplateEntity.setGoalbmileStone3(idpTemplate.getGoalbmileStone3());
            idpTemplateEntity.setGoalbreview(idpTemplate.getGoalbreview());

            log.info("Entity is :" + idpTemplateEntity);

            idpTemplateRepository.save(idpTemplateEntity);
        } catch (Exception e) {
            log.error("Exception occurred while saving idp template ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving idp template " + e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved IDP Template data");
        return response;
    }

    @Override
    public ResponseEntity getIDPLatestVersion(String studentEmail) {
        Response response = new Response();
        try {
            IDPTemplateEntity idpTemplateEntity = idpTemplateRepository.getLatestVersion(studentEmail);
            response.setData(idpTemplateEntity);
            response.setStatusMessage("Successfully received latest version of IDP template for email " + studentEmail);
            response.setStatusCode(200);
        } catch (Exception e) {
            log.error("Exception occurred while getting latest version of IDP template ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting latest version of IDP template " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Override
    public ResponseEntity getIDPTemplate(String studentEmail, Long version) {
        Response response = new Response();
        try {
            List<IDPTemplateEntity> idpTemplateEntityList = idpTemplateRepository.getTemplate(studentEmail, version);
            response.setData(idpTemplateEntityList);
            response.setStatusMessage("Successfully received IDP template data for email " + studentEmail + " and version " + version);
            response.setStatusCode(200);
        } catch (Exception e) {
            log.error("Exception occurred while getting IDP template ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting IDP template " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
