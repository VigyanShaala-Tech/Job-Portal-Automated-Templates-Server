package com.vigyanshaala.controller.jobPortalController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.vigyanshaala.controller.EntitlementController;
import com.vigyanshaala.entity.user.UserRole;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.UserServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServices userServices;
    @Autowired
    EntitlementController entitlementController;

    @Autowired
    CacheManager cacheManager;

    public String getRole(@PathVariable("encryptedEmail") String email) {
        log.info(email);
        ResponseEntity responseEntity;
        Response response = new Response();
        responseEntity = userServices.getUserInfo(email);
        log.info("responseEntity" + responseEntity);
        response = (Response) responseEntity.getBody();
        log.info("response" + response.getData());
        if(Objects.nonNull(response.getData())) {
            UserRole userRole = (UserRole) response.getData();
            log.info(String.valueOf(userRole));
            return userRole.getRole();
        }
        return "None";
    }


    @PostMapping(value="/userRole", produces="application/json")
    @SecurityRequirement(name = "Bearer Authentication")
    Response addUserRole( @RequestHeader("Authorization") String bearerToken, UserRole userRole) {
        Response response=new Response();
        cacheManager.getCache("role-cache").clear();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                String email=idToken.getPayload().getEmail();
                String role=getRole(email);
                if(role.equalsIgnoreCase("Admin"))
                {
                    response = userServices.addUserRole(userRole);

                }else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occured while adding user"+e);
        }
        return response;
}

    @PostMapping(value="/uploadUserFile", consumes="multipart/form-data")
    @SecurityRequirement(name = "Bearer Authentication")
    Response fileUpload(@RequestHeader("Authorization") String bearerToken,@RequestParam("file") MultipartFile file)
    {
        log.info("inside user file upload controller");
        Response response=new Response();
        cacheManager.getCache("role-cache").clear();
        try{
            GoogleIdToken idToken=entitlementController.decodeToken(bearerToken);
            if(Objects.nonNull(idToken)){
                String email=idToken.getPayload().getEmail();
                String role=getRole(email);
                if(role.equalsIgnoreCase("Admin"))
                {
                    response = userServices.uploadUserFile(file);
                }else{
                    log.error("You need admin role to perform this action");
                    response.setStatusCode(HttpStatus.FORBIDDEN.value());
                    response.setStatusMessage("Admin role is missing, please contact the vigyanshaala team");
                }
            }
            else throw new Exception("bearer token is invalid");
        }
        catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while adding user"+e);
        }
        return response;

    }

}
