FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
WORKDIR /home/app
COPY build/libs/workoutsystem.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]