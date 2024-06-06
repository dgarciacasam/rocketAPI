FROM openjdk:21
mvn clean package
ADD ./RocketAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]