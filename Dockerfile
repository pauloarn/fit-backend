FROM eclipse-temurin:11-jdk-alpine
WORKDIR /app
COPY /target/classes/scripts /src/main/resources/scripts
COPY /target/fit-backend-1.0.0.jar app.jar
EXPOSE 8285
ENTRYPOINT ["java","-jar","app.jar"]

