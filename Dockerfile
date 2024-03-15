FROM maven:3-openjdk-17-slim as build

COPY . .

RUN mvn clean package

FROM openjdk:17-jdk-slim

EXPOSE 8285

COPY --from=build /src/main/resources/scripts /src/main/resources/scripts
COPY --from=build /target/fit-backend-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
