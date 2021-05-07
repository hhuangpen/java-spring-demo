FROM openjdk:8-jdk-alpine
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} currency-exchange.jar
ENTRYPOINT ["java","-jar","/currency-exchange.jar"]
