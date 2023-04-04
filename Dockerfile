FROM openjdk:17-jdk-slim-buster
EXPOSE 8080
ADD target/workoutsystem.jar workoutsystem.jar
ENTRYPOINT ["java","-jar","/workoutsystem.jar"]