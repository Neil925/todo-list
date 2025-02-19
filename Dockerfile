# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle build files to cache dependencies
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN chmod +x gradlew && ./gradlew build || return 0

# Copy the entire project
COPY . .

# Build the project
RUN ./gradlew clean build

# Expose port (if the application runs on a specific port)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/todolist-1.0.jar"]

