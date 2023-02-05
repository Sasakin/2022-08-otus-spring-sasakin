FROM openjdk:11-jre-slim
COPY /target/17-book-project-docker.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
