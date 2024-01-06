package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.CollaborationEntity;
import com.vigyanshaala.model.pdfGeneratorModel.Collaboration;
import com.vigyanshaala.repository.pdfGenerator.CollaborationRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CollaborationServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CollaborationServiceImpl implements CollaborationServices {

    private final CollaborationRepository collaborationRepository;
    public CollaborationServiceImpl(CollaborationRepository collaborationRepository){
        this.collaborationRepository = collaborationRepository;

    }

    @Override
    public Response saveCollaborationTemplate(Collaboration collaboration){

        Response response=new Response();
        try {
            CollaborationEntity collaborationEntity = new CollaborationEntity();
            CollaborationEntity collaborationResult = collaborationRepository.getLatestVersion(collaboration.getStudentEmail());
            Long version=0L;
            if(Objects.nonNull(collaborationResult)) {
                version = collaborationResult.getVersion();
                version=version+1;
            }

            log.info("Version " + version);

            collaborationEntity.setStudentId(collaboration.getStudentEmail() + "_" + version);
            collaborationEntity.setStudentEmail(collaboration.getStudentEmail());
            collaborationEntity.setAnswerA(collaboration.getAnswerA());
            collaborationEntity.setAnswerB(collaboration.getAnswerB());
            collaborationEntity.setAnswerC(collaboration.getAnswerC());
            collaborationEntity.setVersion(version);


            log.info("Entity is :"+ collaborationEntity);

            collaborationRepository.save(collaborationEntity);
        }catch(Exception e)
        {
            log.error("Exception occurred while saving Collaboration template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving Collaboration template "+e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved Collaboration Template data");
        return  response;
    }

    @Override
    public ResponseEntity getCollaborationTemplate(String studentEmailID, Long version) {
        Response response=new Response();
        try{
            List<CollaborationEntity> collaborationList=collaborationRepository.getCollaborationTemplate(studentEmailID,version);
            response.setData(collaborationList);
            response.setStatusMessage("Successfully received Collaboration template data for email "+studentEmailID+" and version "+version);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting Collaboration template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting Collaboration template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity getCollaborationLatestVersion(String studentEmailID) {
        Response response=new Response();
        try{
            CollaborationEntity collaborationEntity=collaborationRepository.getLatestVersion(studentEmailID);
            response.setData(collaborationEntity);
            response.setStatusMessage("Successfully received latest version of Collaboration template for email "+studentEmailID);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting latest version of Collaboration template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting latest version of Collaboration template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
