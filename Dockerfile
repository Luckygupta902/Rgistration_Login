# define base docker image
# Use OpenJDK 8 as the base image
FROM openjdk:18

# Set the maintainer label
LABEL maintainer="javaguides.net"

# Add the JAR file to the container
ADD target/autobg1-0.0.1-SNAPSHOT.jar autobg1-0.0.1-SNAPSHOT.jar

# Define the entry point command to run the JAR file
ENTRYPOINT ["java", "-jar", "autobg1-0.0.1-SNAPSHOT.jar"]
