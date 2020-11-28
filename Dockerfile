FROM adoptopenjdk/openjdk11:ubi
RUN mkdir docker
COPY target/accounting-0.0.1-SNAPSHOT.jar docker
CMD ["java", "-jar", "docker/accounting-0.0.1-SNAPSHOT.jar"]