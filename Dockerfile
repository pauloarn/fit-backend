FROM eclipse-temurin:11-jdk-alpine as builder

EXPOSE 8285

COPY --from=builder /src/main/resources/scripts /src/main/resources/scripts
COPY --from=builder /target/fit-backend-1.0.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

