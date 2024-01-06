package com.vigyanshaala.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.vigyanshaala.entity.user.UserRole;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.UserServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * The following Entitlement Controller contains all the get call for the entitlement
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entitlement")
@Slf4j
public class EntitlementController {
    @Value("${client-id}")
    private String clientId;
    @Autowired
    UserServices userServices;
    @Autowired
    CacheManager cacheManager;

    public GoogleIdToken decodeToken(String bearerToken)
    {
        GoogleIdToken idToken;
        try {
            String token = bearerToken.substring(7);
            log.info("token " + token);
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Arrays.asList(clientId))
                    .setIssuer("https://accounts.google.com")
                    .build();

            idToken = verifier.verify(token);
            log.info("ID token:" + idToken);
        }catch(Exception e)
        {
            log.info("Exception occurred in decodeToken : "+e.getMessage());
            return null;
        }
        return idToken;
    }
    public List<String> getUserInfo(@PathVariable("encryptedEmail") String email) {
        log.info(email);
        List<String> lst = new ArrayList<>();
        ResponseEntity responseEntity;
        Response response = new Response();
        responseEntity = userServices.getUserInfo(email);
        log.info("responseEntity" + responseEntity);
        response = (Response) responseEntity.getBody();
        log.info("response" + response.getData());
        if(Objects.nonNull(response.getData())) {
            UserRole userRole = (UserRole) response.getData();
            log.info(String.valueOf(userRole));
            lst.add(userRole.getRole());
            lst.add(userRole.getCohort());
            lst.add(userRole.getCompletionStatus());
            lst.add(userRole.getName());
        }
        else{
            lst.add("None");
            lst.add("");
            lst.add("");

            lst.add("");
        }
        return lst;
    }



    @GetMapping(value="/getRoles", produces="application/json")
    @SecurityRequirement(name="Bearer Authentication")
    public ResponseEntity<Response> role(@RequestHeader("Authorization") String bearerToken) throws IOException {
        log.info("client id "+clientId);
        log.info("bearer token "+bearerToken);
        cacheManager.getCache("role-cache").clear();
        Response response=new Response();
        String name="";
        String email="";
        String role="";
        String cohort="";
        String completionStatus="";
        try {
            GoogleIdToken idToken=decodeToken(bearerToken);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                email=payload.getEmail();
                log.info("Name and email " + name + " " + email);
                List<String> lst = getUserInfo(email);
                response.setStatusCode(HttpStatus.OK.value());
                response.setStatusMessage("Successfully got the role for email "+email);
                role=lst.get(0);
                cohort=lst.get(1);
                completionStatus=lst.get(2);
                name=lst.get(3);
                Map<String,String>entitlementMap=new HashMap<>();
                entitlementMap.put("name",name);
                entitlementMap.put("email",email);
                entitlementMap.put("role",role);
                entitlementMap.put("cohort",cohort);
                entitlementMap.put("completionStatus",completionStatus);
                response.setData(entitlementMap);

            } else {
                log.info("Invalid ID token.");
                throw new Exception("Invalid ID token");
            }
        }catch(Exception e)
        {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception "+e.getMessage()+" occured while getting entitlement for email "+email );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }}

