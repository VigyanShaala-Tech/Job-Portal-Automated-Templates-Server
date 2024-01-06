package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.CriticalThinking;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CriticalThinkingServices;
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
@RequestMapping("/pdf/ct")
@Slf4j
public class CriticalThinkingController {
    @Autowired
    CriticalThinkingServices criticalThinkingServices;
    @Autowired
    EntitlementController entitlementController;


    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createCriticalThinkingTemplate(@RequestHeader("Authorization") String bearerToken,@RequestBody CriticalThinking criticalThinking) {
        Response response = new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                response = criticalThinkingServices.saveCriticalThinkingTemplate(criticalThinking);
            }
            else throw new Exception("bearer token is invalid");
        }
        catch (Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding critical thinking template  " + e.getMessage());
        }
        return response;
    }
    @ApiOperation(value = "Get swot template latest version from the critical thinking template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all swot versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getCTLatestVersion(@RequestHeader("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
                responseEntity = criticalThinkingServices.getCriticalThinkingLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting CT latest version" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get swot template data from the critical thinking template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all swot versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getCriticalThinkingTemplate(@RequestHeader("Authorization")String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                responseEntity= criticalThinkingServices.getCriticalThinkingTemplate(studentEmail,version);
            }else throw new Exception("bearer token is invlid");
        }
        catch (Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting CT Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
