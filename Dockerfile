FROM openjdk:8
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/workoutsystem-0.0.1.jar /app/workoutsystem-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/app/workoutsystem-0.0.1.jar"]
##Bump up java version from 8 to 17