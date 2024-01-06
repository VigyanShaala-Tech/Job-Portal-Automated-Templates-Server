package com.vigyanshaala.serviceImpl.jobPortalServiceImpl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.vigyanshaala.entity.user.UserRole;
import com.vigyanshaala.repository.jobPortalRepository.UserRoleRepository;
import com.vigyanshaala.response.Response;
import com.vigyanshaala.service.jobPortalService.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserRoleServiceImpl implements UserServices {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {

        this.userRoleRepository = userRoleRepository;
    }

    public Response addUserRole(UserRole userRole)
    {
        log.info("inside add user role");
        Response response=new Response();

        List<UserRole>userRoleList= userRoleRepository.findAll();
        log.info("The list is : {}",userRoleList);
        for(UserRole userRole1:userRoleList){
            if(userRole1.getEmailId().equals(userRole.getEmailId()) && userRole1.getRole().equalsIgnoreCase(userRole.getRole()))
            {
                response.setStatusCode(HttpStatus.OK.value());
                response.setStatusMessage("The user already exists in the table with the following role "+userRole1.getRole());
                return response;
            }}

        try {
            userRoleRepository.save(userRole);
            log.info("Successfully saved user");
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Successfully saved user");
        }catch(Exception e)
        {
            log.error("Exception occurred while saving user ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while saving user "+e);
        }
        return response;
    }

    @Override
    public Response uploadUserFile(MultipartFile file) throws IOException {
        log.info("inside upload user file service");
        Response response=new Response();

        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            List<List<String>> records = new ArrayList<List<String>>();
            try (CSVReader csvReader = new CSVReader(new FileReader(convFile));) {
                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                    records.add(Arrays.asList(values));
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException | CsvValidationException e) {
                throw new RuntimeException(e);
            }
            List<UserRole> userRoleList = new ArrayList<>();
            List<String>columns=records.get(0);
            if(columns.size()!=5 || !columns.get(0).equalsIgnoreCase("Role") || !columns.get(1).equalsIgnoreCase("Email") || !columns.get(2).equalsIgnoreCase("Cohort") || !columns.get(3).equalsIgnoreCase("Completion Status") || columns.get(4).equalsIgnoreCase("Name") )
            {
                response.setStatusCode(500);
                response.setStatusMessage("Failed to upload the csv file. Please check the headers of the file. The headers should be in the following format : Role, Email, Cohort, Completion Status, Name");
                return response;

            }
            records.remove(0);
            records.forEach(recordList -> {

                UserRole userRole = new UserRole();
                userRole.setRole(recordList.get(0));
                userRole.setEmailId(recordList.get(1));
                userRole.setCohort(recordList.get(2));
                userRole.setCompletionStatus(recordList.get(3));
                userRole.setName(recordList.get(4));
                userRoleList.add(userRole);
            });
            userRoleRepository.saveAll(userRoleList);
            response.setStatusCode(200);
            response.setStatusMessage("Successfully saved the user file data");
        }catch(Exception e)
        {
            log.info("Exception occurred while saving user file data");
            response.setStatusMessage("Exception "+e.getMessage()+" occurred while saving user file data");
            response.setStatusCode(500);
        }

        return response;
    }

    @Cacheable(value="role-cache")
    public ResponseEntity getUserInfo(String email)
    {
        log.info("inside get Role method");
        ResponseEntity responseEntity;
        Response response=new Response();
        try {
            UserRole userRole = userRoleRepository.findByEmail(email);
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Successfully received userRole");
            response.setData(userRole);

        }catch(Exception e)
        {
            log.error("Exception occurred while getting student name ",e);
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Exception occurred while getting student name "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
