package com.vigyanshaala.service.pdfGeneratorService;

import com.vigyanshaala.model.pdfGeneratorModel.SmartGoalsTemplate;
import com.vigyanshaala.response.Response;
import org.springframework.http.ResponseEntity;


public interface SmartGoalsTemplateServices {

    // Defines three methods related to managing and retrieving SmartGoals templates:

    //method is responsible for saving a SmartGoals template. It takes a SmartGoals object as a parameter, representing the details of the template to be saved. The return type is Response, which likely represents a custom response object encapsulating the result of the save operation.
    Response saveSmartGoalsTemplate(SmartGoalsTemplate smartGoalsTemplate);

    //This method is used to retrieve a specific version of a SmartGoals template. It takes the studentEmailID and version as parameters, representing the identifier of the student and the desired template version, respectively. The return type is ResponseEntity, which is a Spring class representing an HTTP response entity. This allows for more flexibility in constructing and returning HTTP responses.
    ResponseEntity getSmartGoalsTemplate(String studentEmailID, Long version);


    ResponseEntity getSmartGoalsLatestVersion(String studentEmailID);
}

