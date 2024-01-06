package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.Collaboration;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CollaborationServices;
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
@RequestMapping("/pdf/collaboration")
@Slf4j
public class CollaborationController {
    @Autowired
    CollaborationServices collaborationServices;
    @Autowired
    EntitlementController entitlementController;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createCollaborationTemplate(@RequestHeader ("Authorization") String bearerToken,@RequestBody Collaboration collaboration) {
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            log.info("Collaboration template  is : {}" , collaboration.toString());
            response = collaborationServices.saveCollaborationTemplate(collaboration);
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while adding collaborationTemplateData  " , e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding collaborationTemplateData  " + e);
        }
        return response;
    }
    @ApiOperation(value = "Get collaboration template latest version from the collaboration template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all collaboration versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getCollaborationLatestVersion(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= collaborationServices.getCollaborationLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting collaboration latest version "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting collaboration latest version"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get collaboration template data from the collaboration template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all collaboration versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getCollaborationTemplate(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{

            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= collaborationServices.getCollaborationTemplate(studentEmail,version);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting Collaboration Template data "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting Collaboration Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
