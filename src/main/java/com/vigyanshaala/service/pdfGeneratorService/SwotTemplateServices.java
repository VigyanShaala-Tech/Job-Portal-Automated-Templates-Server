
package com.vigyanshaala.service.pdfGeneratorService;

import com.vigyanshaala.model.pdfGeneratorModel.SwotTemplate;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;

public interface SwotTemplateServices {

    Response saveSwotTemplate(SwotTemplate swotTemplate);
    ResponseEntity getSwotTemplate(String studentEmailID,Long version);
    ResponseEntity getSwotLatestVersion(String studentEmailID);
}
