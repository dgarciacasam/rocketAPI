FROM openjdk:21
ADD ./target/RocketAPI-0.0.1-SNAPSHOT.jar app.jar
ARG PORT=8080
ENV PORT=${PORT}
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]