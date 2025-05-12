FROM gradle:8.13-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build -x test

FROM openjdk:25-slim

WORKDIR /app

RUN apt-get update && apt-get install -y curl

COPY --from=build /app/build/libs/demo-BOOKING_APP.jar BOOKING_APP.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "BOOKING_APP.jar"]