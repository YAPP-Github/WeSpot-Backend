FROM openjdk:17
COPY app/build/libs/wespot.jar wespot.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev", "wespot.jar"]
