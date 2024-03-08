FROM ubuntu as build

RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:11-jdk-slim

EXPOSE 8285

COPY --from=build /src/main/resources/scripts /src/main/resources/scripts
COPY --from=build /target/fit-backend-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
