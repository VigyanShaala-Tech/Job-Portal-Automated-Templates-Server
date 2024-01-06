package com.vigyanshaala.service.pdfGeneratorService;

import com.vigyanshaala.model.pdfGeneratorModel.IDPTemplate;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;

public interface IDPTemplateServices {
    Response saveIDPTemplate(IDPTemplate idpTemplate);

    ResponseEntity getIDPTemplate(String studentEmailID, Long version);

    ResponseEntity getIDPLatestVersion(String studentEmailID);
}
