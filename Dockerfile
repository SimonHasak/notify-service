FROM openjdk:11.0.6-jre-slim
COPY target/notification-service-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081