package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.SmartGoalsTemplateEntity;
import com.vigyanshaala.model.pdfGeneratorModel.SmartGoalsTemplate;
import com.vigyanshaala.repository.pdfGenerator.SmartGoalsTemplateRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.SmartGoalsTemplateServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SmartGoalsTemplateServiceImpl implements SmartGoalsTemplateServices {

    private final SmartGoalsTemplateRepository smartGoalsTemplateRepository;
    public SmartGoalsTemplateServiceImpl(SmartGoalsTemplateRepository smartGoalsTemplateRepository){
        this.smartGoalsTemplateRepository = smartGoalsTemplateRepository;

    }

    @Override
    public Response saveSmartGoalsTemplate(SmartGoalsTemplate smartGoalsTemplate){

        Response response=new Response();
        try {
            SmartGoalsTemplateEntity smartGoalsTemplateEntity = new SmartGoalsTemplateEntity();
            SmartGoalsTemplateEntity smartGoalsTemplateEntityResult = smartGoalsTemplateRepository.getLatestVersion(smartGoalsTemplate.getStudentEmail());
            Long version=0L;
            if(Objects.nonNull(smartGoalsTemplateEntityResult)) {
                version = smartGoalsTemplateEntityResult.getVersion();
                version=version+1;
            }

            log.info("Version " + version);

            smartGoalsTemplateEntity.setStudentId(smartGoalsTemplate.getStudentEmail() + "_" + version);
            smartGoalsTemplateEntity.setStudentEmail(smartGoalsTemplate.getStudentEmail());
            smartGoalsTemplateEntity.setGoal(smartGoalsTemplate.getGoal());
            smartGoalsTemplateEntity.setTimeBound(smartGoalsTemplate.getTimeBound());
            smartGoalsTemplateEntity.setRelevant(smartGoalsTemplate.getRelevant());
            smartGoalsTemplateEntity.setSpecific(smartGoalsTemplate.getSpecific());
            smartGoalsTemplateEntity.setMeasurable(smartGoalsTemplate.getMeasurable());
            smartGoalsTemplateEntity.setAchievable(smartGoalsTemplate.getAchievable());
            smartGoalsTemplateEntity.setVersion(version);


            log.info("Entity is :"+ smartGoalsTemplateEntity);

            smartGoalsTemplateRepository.save(smartGoalsTemplateEntity);
        }catch(Exception e)
        {
            log.error("Exception occurred while saving smartGoals template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving smartGoals template "+e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved SmartGoals Template data");
        return  response;
    }
    @Override
    public ResponseEntity getSmartGoalsLatestVersion(String studentEmail)
    {
        Response response=new Response();
        try{
            SmartGoalsTemplateEntity smartGoalsTemplateEntity=smartGoalsTemplateRepository.getLatestVersion(studentEmail);
            response.setData(smartGoalsTemplateEntity);
            response.setStatusMessage("Successfully received latest version of smartGoals template for email "+studentEmail);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting latest version of smartGoals template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting latest version of smartGoals template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Override
    public ResponseEntity getSmartGoalsTemplate(String studentEmail, Long version)
    {
        Response response=new Response();
        try{
            List<SmartGoalsTemplateEntity> swotTemplateList=smartGoalsTemplateRepository.getTemplate(studentEmail,version);
            response.setData(swotTemplateList);
            response.setStatusMessage("Successfully received smartGoals template data for email "+studentEmail+" and version "+version);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting smartGoals template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting smartGoals template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
