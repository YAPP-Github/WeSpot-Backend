FROM openjdk:17
COPY app/build/libs/app-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev", "app.jar"]
