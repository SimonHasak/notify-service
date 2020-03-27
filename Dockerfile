FROM openjdk:11.0.6-jre-slim
COPY target/notify-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "notify-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081