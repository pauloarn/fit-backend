FROM gradle:8.6-jdk17 AS build

WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8285

COPY --from=build /app/src/main/resources/scripts /src/main/resources/scripts
COPY --from=build /app/build/libs/fit-backend.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
