package com.vigyanshaala.serviceImpl.pdfGeneratorServiceImpl;

import com.vigyanshaala.service.pdfGeneratorService.RiasecServices;
import com.vigyanshaala.entity.pdfGeneratorEntity.RiasecEntity;
import com.vigyanshaala.model.pdfGeneratorModel.RiasecTemplate;
import com.vigyanshaala.repository.pdfGenerator.RiasecRepository;
import com.vigyanshaala.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RiasecServiceImpl implements RiasecServices{

    private final RiasecRepository riasecRepository;
    public RiasecServiceImpl(RiasecRepository riasecRepository){
        this.riasecRepository = riasecRepository;
    }

    @Override
    public Response saveRiasecTemplate(RiasecTemplate riasecTemplate) {
        Response response = new Response();
        try {
            RiasecEntity riasecEntity = new RiasecEntity();
            RiasecEntity riasecResult = riasecRepository.getLatestVersion(riasecTemplate.getStudentEmail());
            Long version = 0L;
            if (Objects.nonNull(riasecResult)) {
                version = riasecResult.getVersion();
                version = version + 1;
            }

            log.info("Version " + version);

            riasecEntity.setStudentId(riasecTemplate.getStudentEmail() + "_" + version);
            riasecEntity.setStudentEmail(riasecTemplate.getStudentEmail());
            riasecEntity.setRealistic(riasecTemplate.getRealistic());
            riasecEntity.setInvestigative(riasecTemplate.getInvestigative());
            riasecEntity.setArtistic(riasecTemplate.getArtistic());
            riasecEntity.setSocial(riasecTemplate.getSocial()      );
            riasecEntity.setEnterprising(riasecTemplate.getEnterprising());
            riasecEntity.setConventional(riasecTemplate.getConventional());
            riasecEntity.setHollandCode(riasecTemplate.getHollandCode());
            riasecEntity.setVersion(version);



            log.info("Entity is :" + riasecEntity);

            riasecRepository.save(riasecEntity);
        } catch (Exception e) {
            log.error("Exception occurred while saving Riasec template ", e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving Riasec template " + e);
        }


        response.setStatusCode(200);
        response.setStatusMessage("Successfully saved Riasec Template data");
        return response;

    }

        @Override
        public ResponseEntity getRiasecTemplate(String studentEmailID, Long version) {
            Response response=new Response();
            try{
                List<RiasecEntity> RiasecList=riasecRepository.getTemplate(studentEmailID,version);
                response.setData(RiasecList);
                response.setStatusMessage("Successfully received critical thinking template data for email "+studentEmailID+" and version "+version);
                response.setStatusCode(200);
            }catch(Exception e)
            {
                log.error("Exception occurred while getting Riasec template ",e);
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setStatusMessage("Exception occurred while getting Riasec  template "+e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Override
        public ResponseEntity getRiasecLatestVersion(String studentEmailID) {

            Response response=new Response();
            try{
                RiasecEntity riasecEntity=riasecRepository.getLatestVersion(studentEmailID);
                response.setData(riasecEntity);
                response.setStatusMessage("Successfully received latest version of riasec template for email "+studentEmailID);
                response.setStatusCode(200);
            }catch(Exception e)
            {
                log.error("Exception occurred while getting latest version of riasec template ",e);
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setStatusMessage("Exception occurred while getting latest version of riasec template "+e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }


