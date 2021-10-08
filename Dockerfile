#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS builder
#WORKDIR /workspace/source
#WORKDIR /app
MAINTAINER  David Machacek <davido.machacek@gmail.com>
# copy source inside container
RUN pwd
RUN ls
RUN ls /workspace/
RUN ls /workspace/source/
RUN ls /workspace/source/src/
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
COPY --from=builder /workspace/source/target/*.jar /app.jar
# run application with this command line
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]
