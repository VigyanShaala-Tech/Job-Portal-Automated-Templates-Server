package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.SwotTemplate;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.SwotTemplateServices;
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
@RequestMapping("/pdf/swot")
@Slf4j
public class SwotController {
    @Autowired
    SwotTemplateServices swotTemplateServices;
    @Autowired
    EntitlementController entitlementController;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createSwotTemplate(@RequestHeader("Authorization") String bearerToken,@RequestBody SwotTemplate swotTemplate) {
        Response response = new Response();
        log.info("createSwotTemplate");
        try {
            GoogleIdToken idToken= entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                    response = swotTemplateServices.saveSwotTemplate(swotTemplate);
            } else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding swotTemplateData  " + e);
        }
        return response;
    }
    @ApiOperation(value = "Get swot template latest version from the swot template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all swot versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getSwotLatestVersion(@RequestHeader("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        log.info("getSwotLatestVersion");
        try{
            GoogleIdToken idToken= entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)) {
                responseEntity = swotTemplateServices.getSwotLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting swot latest version"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get swot template data from the swot template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all swot versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getSwotTemplate(@RequestHeader("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        log.info("getSwotTemplate");
        try{
            GoogleIdToken idToken= entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                responseEntity= swotTemplateServices.getSwotTemplate(studentEmail,version);
            }else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting Swot Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
