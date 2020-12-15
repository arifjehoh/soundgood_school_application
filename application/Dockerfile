FROM openjdk:8-jre-alpine
ARG JAR_FILE=target/application-1.0-SNAPSHOT.jar
ARG JAR_LIB_FILE=lib/
COPY ${JAR_FILE} application.jar
ADD ${JAR_LIB_FILE} lib/
ENTRYPOINT ["java","-jar","application.jar"]
EXPOSE 8080
