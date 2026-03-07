FROM eclipse-temurin:21-jre
COPY app/build/libs/app-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "app.jar"]
