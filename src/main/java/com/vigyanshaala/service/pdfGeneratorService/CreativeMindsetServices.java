package com.vigyanshaala.service.pdfGeneratorService;
import com.vigyanshaala.model.pdfGeneratorModel.CreativeMindset;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;


public interface CreativeMindsetServices {
    Response saveCreativeMindsetTemplate(CreativeMindset creativeMindset);
    ResponseEntity getCreativeMindsetTemplate(String studentEmailID, Long version);
    ResponseEntity getCreativeMindsetLatestVersion(String studentEmailID);
}
