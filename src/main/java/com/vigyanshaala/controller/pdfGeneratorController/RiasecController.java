package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.RiasecTemplate;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.RiasecServices;
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
@RequestMapping("/pdf/riasec")
@Slf4j
public class RiasecController {
    @Autowired
    RiasecServices riasecServices;
    @Autowired
    EntitlementController entitlementController;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createRiasecTemplate(@RequestHeader ("Authorization") String bearerToken,@RequestBody RiasecTemplate riasecTemplate) {
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            log.info("The riasec template  is : {}" , riasecTemplate.toString());
            response = riasecServices.saveRiasecTemplate(riasecTemplate);
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while adding Riasec template data  " , e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding riasec template  " + e);
        }
        return response;
    }
    @ApiOperation(value = "Get riasec template latest version from the riasec template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all swot versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getRiasecLatestVersion(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{

            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= riasecServices.getRiasecLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting RI latest version "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting RI latest version"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get Riasec template data from the Riasec template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all riasec versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getRiasecTemplate(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{

            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= riasecServices.getRiasecTemplate(studentEmail,version);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting Riasec Template data "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting Riasec Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
