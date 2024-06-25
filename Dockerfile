# Use an official OpenJDK runtime as a parent image
FROM public.ecr.aws/docker/library/openjdk:21-ea-11-jdk-slim

# Set the working directory inside the container
WORKDIR /opt/app

# Copy the Spring Boot JAR file into the container
COPY target/finforz-backend-1.jar /opt/app/finforz-backend-1.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8085

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "finforz-backend-1.jar"]
