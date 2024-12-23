# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim
# Add a volume pointing to /tmp
VOLUME /tmp
# Make port 9600 available to the world outside this container
EXPOSE 8080
# Disable caching for the following steps
ARG CACHEBUST=1
# The application's jar file
ARG JAR_FILE=target/Vigyanshaala-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} Vigyanshaala-0.0.1-SNAPSHOT.jar
# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Vigyanshaala-0.0.1-SNAPSHOT.jar"]
