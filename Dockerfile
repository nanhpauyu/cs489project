FROM amazoncorretto:23

WORKDIR /app

COPY build/libs/cs489project-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT["java","-jar","cs489project-0.0.1-SNAPSHOT.jar"]