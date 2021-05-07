FROM openjdk:8-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} naming-server.jar
CMD ["java", "-jar", "naming-server.jar"]
