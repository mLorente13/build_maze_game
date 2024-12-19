FROM maven:3.9.5-openjdk-21 AS build
COPY ../pom.xml /pom.xml
RUN mvn clean package -DskipTests

FROM openjdk:21.0.1-jdk-slim
COPY --from=build /target/maze-0.0.1.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
