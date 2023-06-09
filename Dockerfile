FROM gradle:jdk17 as builder

WORKDIR /app

COPY . /app

RUN gradle build --no-daemon --exclude-task test


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/foodrec.jar foodrec.jar
COPY firebase-config.json /app/firebase-config.json


ENTRYPOINT ["java", "-jar", "foodrec.jar"]