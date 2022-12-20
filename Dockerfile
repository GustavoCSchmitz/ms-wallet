FROM maven:3.8.6-openjdk-18-slim AS build
COPY pom.xml .
COPY src src
RUN mvn clean install

FROM openjdk:17-jdk-slim AS release
COPY --from=build /target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
