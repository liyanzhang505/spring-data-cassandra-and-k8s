FROM adoptopenjdk/openjdk15:latest

ADD build/libs/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]