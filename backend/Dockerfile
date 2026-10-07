FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline
COPY src src
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache curl && addgroup -S orderflow && adduser -S orderflow -G orderflow
WORKDIR /app
COPY --from=build --chown=orderflow:orderflow /app/target/orderflow-*.jar app.jar
USER orderflow
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
