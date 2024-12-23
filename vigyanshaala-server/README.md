# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This repository contains the entire backend code for Vigyanshaala Job Portal and Pdf Generator Services



### Versions ###

* The code uses Java version 17 and Spring Boot version 3.1


### Steps to start the project ###

* Clone this project and open in intelliJ
* Download the correct java version and select it in intelliJ [ file -> project -> sdk section ]
* Run the [mvn clean install] command in the terminal or from the [Maven -> Vigyanshaala -> Lifecycle] section on the right
* Build the project using the build project option on the top

[Steps to use profile based configuration in application.yml]
* application.yml is the default file. It has the production configurations along with the application-prod.yml.
* application-local.yml has the local configurations and can be used for testing
* For using the application-local.yml :
        cd to the target folder and run the command [java -jar -Dspring.profiles.active=prod Vigyanshaala-0.0.1-SNAPSHOT.jar]
* Use the below links for testing in swagger, use the [bearer token without the bearer keyword] in the authorize and the authorization header
* Get the bearer token from the developer console in the UI when logging in


### Important Links ###

* Swagger link : https://api.vigyanshaala.org/swagger-ui/index.html




