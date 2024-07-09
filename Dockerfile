# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as build

# Add author
LABEL authors="harjeevansingh"

# Set the working directory in the container
WORKDIR /workspace/app

# Copy Maven wrapper files
COPY mvnw .
COPY .mvn .mvn

# Copy the project file
COPY pom.xml .

# Copy source code
COPY src src

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw install -DskipTests

# Create a new stage with a minimal image for the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /workspace/app/target/chatservice-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app/app.jar"]