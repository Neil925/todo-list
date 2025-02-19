# Use an official OpenJDK runtime as a base image
FROM openjdk:23-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy build output (compiled classes, resources, and config)
COPY app/build/classes/java/main /app/classes
COPY app/build/resources/main /app/resources
COPY app/bin/main/hibernate.cfg.xml /app/resources/hibernate.cfg.xml

# Set the classpath and define the entry point
CMD ["java", "-cp", "/app/classes:/app/resources", "org.example.App"]
