package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.entity.pdfGeneratorEntity.CreativeMindsetEntity;
import com.vigyanshaala.model.pdfGeneratorModel.CreativeMindset;
import com.vigyanshaala.repository.pdfGenerator.CreativeMindsetRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CreativeMindsetServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CreativeMindsetServiceImpl implements CreativeMindsetServices {

    private final CreativeMindsetRepository creativeMindsetRepository;
    public CreativeMindsetServiceImpl(CreativeMindsetRepository creativeMindsetRepository){
        this.creativeMindsetRepository = creativeMindsetRepository;

    }

    @Override
    public Response saveCreativeMindsetTemplate(CreativeMindset creativeMindset){

        Response response=new Response();
        try {
            CreativeMindsetEntity creativeMindsetEntity = new CreativeMindsetEntity();
            CreativeMindsetEntity creativeMindsetResult = creativeMindsetRepository.getCreativeMindsetLatestVersion(creativeMindset.getStudentEmail());
            Long version=0L;
            if(Objects.nonNull(creativeMindsetResult)) {
                version = creativeMindsetResult.getVersion();
                version=version+1;
            }

            log.info("Version " + version);

            creativeMindsetEntity.setStudentId(creativeMindset.getStudentEmail() + "_" + version);
            creativeMindsetEntity.setStudentEmail(creativeMindset.getStudentEmail());
            creativeMindsetEntity.setAnswerA(creativeMindset.getAnswerA());
            creativeMindsetEntity.setAnswerB(creativeMindset.getAnswerB());
            creativeMindsetEntity.setAnswerC(creativeMindset.getAnswerC());
            creativeMindsetEntity.setVersion(version);


            log.info("Entity is :"+ creativeMindsetEntity);

            creativeMindsetRepository.save(creativeMindsetEntity);
        }catch(Exception e)
        {
            log.error("Exception occurred while saving Creative Mindset template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving Creative Mindset template "+e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved Creative Mindset Template data");
        return  response;
    }


    @Override
    public ResponseEntity getCreativeMindsetTemplate(String studentEmailID, Long version) {
        Response response=new Response();
        try{
            List<CreativeMindsetEntity> creativeMindsetList=creativeMindsetRepository.getCreativeMindsetTemplate(studentEmailID,version);
            response.setData(creativeMindsetList);
            response.setStatusMessage("Successfully received Creative mindset template data for email "+studentEmailID+" and version "+version);
            response.setStatusCode(200);
        }catch(Exception e)
        {
            log.error("Exception occurred while getting Creative mindset template ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting Creative mindset template "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity getCreativeMindsetLatestVersion(String studentEmailID) {
        Response response=new Response();
        try{
            CreativeMindsetEntity creativeMindsetEntity=creativeMindsetRepository.getCreativeMindsetLatestVersion(studentEmailID);
            response.setData(creativeMindsetEntity);
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
