FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/maze-0.0.1.jar maze.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "maze.jar"]
