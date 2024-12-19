FROM openjdk:21-jdk-slim
ARG WAR_FILE=target/maze-0.0.1.war
COPY ${WAR_FILE} maze_game.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "maze_game.war"]
