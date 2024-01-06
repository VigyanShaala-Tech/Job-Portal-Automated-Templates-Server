package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.CriticalThinkingEntity;
import com.vigyanshaala.model.pdfGeneratorModel.CriticalThinking;
import com.vigyanshaala.repository.pdfGenerator.CriticalThinkingRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CriticalThinkingServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CriticalThinkingServiceImpl implements CriticalThinkingServices {
    private final CriticalThinkingRepository criticalThinkingRepository;
    public CriticalThinkingServiceImpl(CriticalThinkingRepository criticalThinkingRepository){
        this.criticalThinkingRepository = criticalThinkingRepository;

    }

    @Override
    public Response saveCriticalThinkingTemplate(CriticalThinking criticalThinking) {
        Response response=new Response();
        try {
            CriticalThinkingEntity criticalThinkingEntity = new CriticalThinkingEntity();
            CriticalThinkingEntity criticalThinkingResult = criticalThinkingRepository.getLatestVersion(criticalThinking.getStudentEmail());
            Long version=0L;
            if(Objects.nonNull(criticalThinkingResult)) {
                version = criticalThinkingResult.getVersion();
                version=version+1;
            }

            log.info("Version " + version);

            criticalThinkingEntity.setStudentId(criticalThinking.getStudentEmail() + "_" + version);
            criticalThinkingEntity.setStudentEmail(criticalThinking.getStudentEmail());
            criticalThinkingEntity.setAnswerA(criticalThinking.getAnswerA());
            criticalThinkingEntity.setAnswerB(criticalThinking.getAnswerB());
            criticalThinkingEntity.setAnswerC(criticalThinking.getAnswerC());
            criticalThinkingEntity.setVersion(version);


            log.info("Entity is :"+ criticalThinkingEntity);

            criticalThinkingRepository.save(criticalThinkingEntity);
        }catch(Exception e)
        {
            log.error("Exception occurred while saving Critical thinking template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving Critical thinking template "+e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved Critical Thinking Template data");
        return  response;
    }

    @Override
    public ResponseEntity getCriticalThinkingTemplate(String studentEmailID, Long version) {
        Response response=new Response();
        try{
            List<CriticalThinkingEntity> criticalThinkingList=criticalThinkingRepository.getTemplate(studentEmailID,version);
            response.setData(criticalThinkingList);
            response.setStatusMessage("Successfully received critical thinking template data for email "+studentEmailID+" and version "+version);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting critical thinking template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting critical thinking template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity getCriticalThinkingLatestVersion(String studentEmailID) {
        Response response=new Response();
        try{
            CriticalThinkingEntity criticalThinkingEntity=criticalThinkingRepository.getLatestVersion(studentEmailID);
            response.setData(criticalThinkingEntity);
            response.setStatusMessage("Successfully received latest version of critical thinking template for email "+studentEmailID);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting latest version of critical thinking template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting latest version of critical thinking template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
