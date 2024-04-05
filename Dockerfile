FROM openjdk:17-slim

COPY build/libs/car-rental-0.0.1-SNAPSHOT.jar car-rental.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/car-rental.jar"]