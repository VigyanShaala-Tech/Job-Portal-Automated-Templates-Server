package com.vigyanshaala.controller.pdfGeneratorController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.model.pdfGeneratorModel.CreativeMindset;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.pdfGeneratorService.CreativeMindsetServices;
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
@RequestMapping("/pdf/creativemindset")
@Slf4j
public class CreativeMindsetController {
    @Autowired
    CreativeMindsetServices creativeMindsetServices;
    @Autowired
    EntitlementController entitlementController;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response createCreativeMindsetTemplate(@RequestHeader ("Authorization") String bearerToken,@RequestBody CreativeMindset creativeMindset) {
        Response response = new Response();
        try {
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            log.info("creativeMindset template  is : {}" , creativeMindset.toString());
            response = creativeMindsetServices.saveCreativeMindsetTemplate(creativeMindset);
            } else throw new Exception("bearer token is invalid");
        } catch (Exception e) {
            log.error("Exception occurred while adding swotTemplateData  " , e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding creativemindsetTemplateData  " + e);
        }
        return response;
    }
    @ApiOperation(value = "Get swot template latest version from the swot template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all creative mindset versions.")
    @GetMapping(value="/version/{studentEmail}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity getCreativeMindsetLatestVersion(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity= creativeMindsetServices.getCreativeMindsetLatestVersion(studentEmail);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting creativemindset latest version "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting creativemindset latest version"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }

    @ApiOperation(value = "Get creativemindset template data from the creativemindset template table", notes = "Returns a response entity with status code 200 and response in the body. The response data contains the list of all creativemindset versions.")
    @GetMapping(value="/{studentEmail}/{version}", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Response> getCreativeMindsetTemplate(@RequestHeader ("Authorization") String bearerToken,@PathVariable("studentEmail") String studentEmail,@PathVariable("version") Long version){
        ResponseEntity responseEntity;
        Response response=new Response();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if (Objects.nonNull(idToken)) {
            responseEntity=  creativeMindsetServices.getCreativeMindsetTemplate(studentEmail,version);
            } else throw new Exception("bearer token is invalid");
        }catch(Exception e){
            log.error("Exception occurred while getting CreativeMindset Template data "+e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while getting CreativeMindset Template data"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return responseEntity;
    }
}
