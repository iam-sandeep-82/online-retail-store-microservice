FROM maven:3.9-eclipse-temurin-17-alpine
RUN mkdir -p /src
WORKDIR /src
COPY ./shopping-client/target/shopping-client-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "shopping-client-0.0.1-SNAPSHOT.jar"]
