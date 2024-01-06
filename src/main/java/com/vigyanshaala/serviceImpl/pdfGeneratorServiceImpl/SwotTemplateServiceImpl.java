package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.SwotTemplateEntity;
import com.vigyanshaala.model.pdfGeneratorModel.SwotTemplate;
import com.vigyanshaala.repository.pdfGenerator.SwotTemplateRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.SwotTemplateServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SwotTemplateServiceImpl implements SwotTemplateServices {

    private final SwotTemplateRepository swotTemplateRepository;
    public SwotTemplateServiceImpl(SwotTemplateRepository swotTemplateRepository){
        this.swotTemplateRepository = swotTemplateRepository;

    }

    @Override
    public Response saveSwotTemplate(SwotTemplate swotTemplate){

        Response response=new Response();
        try {
            SwotTemplateEntity swotTemplateEntity = new SwotTemplateEntity();
            SwotTemplateEntity swotTemplateEntityResult = swotTemplateRepository.getLatestVersion(swotTemplate.getStudentEmail());
            Long version=0L;
            if(Objects.nonNull(swotTemplateEntityResult)) {
                 version = swotTemplateEntityResult.getVersion();
                 version=version+1;
            }

            log.info("Version " + version);

            swotTemplateEntity.setStudentId(swotTemplate.getStudentEmail() + "_" + version);
            swotTemplateEntity.setStudentEmail(swotTemplate.getStudentEmail());
            swotTemplateEntity.setGoal(swotTemplate.getGoal());
            swotTemplateEntity.setStudentName(swotTemplate.getStudentName());
            swotTemplateEntity.setThreat(swotTemplate.getThreat());
            swotTemplateEntity.setOpportunity(swotTemplate.getOpportunity());
            swotTemplateEntity.setStrength(swotTemplate.getStrength());
            swotTemplateEntity.setWeakness(swotTemplate.getWeakness());
            swotTemplateEntity.setStudentDegree(swotTemplate.getStudentDegree());
            swotTemplateEntity.setVersion(version);


            log.info("Entity is :"+ swotTemplateEntity);

            swotTemplateRepository.save(swotTemplateEntity);
        }catch(Exception e)
        {
            log.error("Exception occurred while saving swot template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving job location "+e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved Swot Template data");
        return  response;
    }
    @Override
    public ResponseEntity getSwotLatestVersion(String studentEmail)
    {
        Response response=new Response();
        try{
            SwotTemplateEntity swotTemplateEntity=swotTemplateRepository.getLatestVersion(studentEmail);
            response.setData(swotTemplateEntity);
            response.setStatusMessage("Successfully received latest version of swot template for email "+studentEmail);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting latest version of swot template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting latest version of swot template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Override
    public ResponseEntity getSwotTemplate(String studentEmail, Long version)
    {
        Response response=new Response();
        try{
            List<SwotTemplateEntity> swotTemplateList=swotTemplateRepository.getTemplate(studentEmail,version);
            response.setData(swotTemplateList);
            response.setStatusMessage("Successfully received swot template data for email "+studentEmail+" and version "+version);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting swot template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting swot template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
