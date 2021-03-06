#### Stage 1: Build the application
FROM gradle:6.2.2-jdk8 as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
#RUN gradle build -x test
RUN gradle build

#### Stage 2: A minimal docker image with command to run the app
FROM openjdk:8-jre-slim

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/FiapCard.jar

# Runs application
ENTRYPOINT [ "java", "-jar", "/app/FiapCard.jar"]