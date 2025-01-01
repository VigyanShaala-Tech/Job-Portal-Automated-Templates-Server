# Local Setup: UI and Backend Setup for VigyanShaala Project

## Part A: Server Setup

### 1. Prerequisites Installation

#### Java Development Kit (JDK) 17
- Download JDK 17 from [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or [OpenJDK](https://openjdk.org/).
- Install JDK and note the installation path.

#### Apache Maven
- Download Maven from [Apache Maven Download](https://maven.apache.org/download.cgi).
- Extract and note the `bin` folder path.

#### IntelliJ IDEA
- Download and install IntelliJ IDEA from [JetBrains](https://www.jetbrains.com/idea/).

#### PostgreSQL
- Download and install PostgreSQL from [PostgreSQL Downloads](https://www.postgresql.org/download/).

### 2. Configure Environment Variables

#### Steps:
- Add the `bin` paths for JDK and Maven to your system's environment variables:

  - **Windows:**
    - Open "System Properties" > "Environment Variables."
    - Under "System Variables," add new paths for JDK and Maven `bin`.
- Restart the terminal to apply changes.

### 3. Create Database in PostgreSQL
- Open PostgreSQL client (e.g., `psql` or `pgAdmin`).
- Create the database:
  ```sql
  CREATE DATABASE vigyanshaala;
  ```
- Run the `create.sql` file from your project:
  ```sql
  \i path/to/create.sql;
  ```

### 4. Update `application.yml`
- Open the `application.yml` file in the backend project.
- Update the PostgreSQL credentials:
  ```yaml
  spring:
    datasource:
      url: jdbc:postgresql://localhost:port/database_name
      username: your_username
      password: your_password
  ```

---

## Part C: Running the Code

### 1. Start the Backend
- Open IntelliJ IDEA and load the backend project.
- Open the terminal in IntelliJ and run:
  ```bash
  mvn clean install
  ```
- Navigate to the target directory:
  ```bash
  cd target
  ```
- Start the backend application:
  ```bash
  java -jar Vigyanshaala-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
  ```

---

## Part D: Post-Setup Tasks

### 1. Add `user_role` to PostgreSQL
- Open the PostgreSQL client.
- Insert `user_role` entries into the database:
  ```sql
  INSERT INTO user_role (email_id, cohort, completion_status, name, role)
  VALUES ('user@gmail.com', 'incubator', 'yes', 'user_name', 'student');
  ```

-------------------------------------------------------------------------------------------------------------------

# Old README #

The Below README would normally document whatever steps are necessary to get your application up and running.

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









