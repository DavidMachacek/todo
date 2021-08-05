#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS builder
WORKDIR /app
MAINTAINER  David Machacek <davido.machacek@gmail.com>
# copy source inside container
COPY src src
COPY pom.xml .
# build, build, build!
RUN mvn package

#
# Run stage
#
# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
RUN apk update && apk add bash && apk add curl
# copy JAR from previous stage into image
COPY --from=builder /app/target/*.jar /app.jar
# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]