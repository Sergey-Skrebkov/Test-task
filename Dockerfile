FROM maven:3.9.6-amazoncorretto-17 as builder
LABEL authors="sergejskrebkov"
WORKDIR /app
COPY ./ /app

RUN mvn install

FROM openjdk:17
COPY --from=builder /app/target/test-task-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]