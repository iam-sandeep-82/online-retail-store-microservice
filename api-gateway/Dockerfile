FROM maven:3.9-eclipse-temurin-17-alpine
RUN mkdir -p /src
WORKDIR /src
COPY $PWD/target/*.jar .
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]