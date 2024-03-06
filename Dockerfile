FROM eclipse-temurin:11-jdk-alpine as build

EXPOSE 8285

COPY --from=build /src/main/resources/scripts /src/main/resources/scripts
COPY --from=build /target/fit-backend-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

