FROM gradle:7-jdk17-alpine   as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build

FROM openjdk:17-alpine
COPY --from=gradleimage /home/gradle/source/build/libs/peigobackend-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
EXPOSE 9000
ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "peigobackend-0.0.1-SNAPSHOT.jar"]