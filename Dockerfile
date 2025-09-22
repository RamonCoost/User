FROM gradle:9.0.0-jdk21 as BUILD
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=BUILD /app/build/libs/*.jar /app/User.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/User.jar"]