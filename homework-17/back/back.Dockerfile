FROM openjdk:17.0.2-oracle
COPY back/target/homework-17-0.0.1-SNAPSHOT.jar /back/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/back/app.jar"]
