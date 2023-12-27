FROM ubuntu:latest
LABEL authors="Mauro Baioni"

FROM gradle:8.5.0-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build -x test --no-daemon

FROM azul/zulu-openjdk-alpine:17-latest

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=postgres

EXPOSE 5000

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/api-box.jar /app/

ENTRYPOINT ["java","-jar","/app/api-box.jar"]
