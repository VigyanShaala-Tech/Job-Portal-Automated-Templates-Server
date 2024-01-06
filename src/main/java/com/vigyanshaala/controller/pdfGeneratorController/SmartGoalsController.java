package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.SmartGoalsTemplate;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.SmartGoalsTemplateServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pdf/smartgoals")
@Slf4j
public class SmartGoalsController {
    @Autowired
    SmartGoalsTemplateServices smartGoalsTemplateServices;
    @Autowired
    EntitlementController entitlementController;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createSmartGoalsTemplate(@RequestHeader ("Authorization") String bearerToken,@RequestBody SmartGoalsTemplate smartGoalsTemplate) {
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            log.info("The smartGoals template  is : {}" , smartGoalsTemplate.toString());
            response = smartGoalsTemplateServices.saveSmartGoalsTemplate(smartGoalsTemplate);
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while adding smartGoalsTemplateData  " , e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding smartGoalsTemplateData  " + e);
        }
        return response;
    }
    @ApiOperation(value = "Get smartGoals template latest version from the swot template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all smartGoals versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getSmartGoalsLatestVersion(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{

            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= smartGoalsTemplateServices.getSmartGoalsLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting smartGoals latest version "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting smartGoals latest version"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get swot template data from the smartGoals template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all smartGoals versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getSmartGoalsTemplate(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{

            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= smartGoalsTemplateServices.getSmartGoalsTemplate(studentEmail,version);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting SmartGoals Template data "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting SmartGoals Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
