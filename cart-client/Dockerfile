
FROM maven:3.9-eclipse-temurin-17-alpine
RUN mkdir -p /jar
WORKDIR /jar
COPY $PWD/target/*.jar .
ENTRYPOINT ["java", "-jar", "cart-service-1.jar"]
