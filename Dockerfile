FROM openjdk:11 AS Builder
RUN apt-get update
RUN apt-get install -y maven
COPY ./backend/pom.xml /torcedores-app/pom.xml
COPY ./backend/src /torcedores-app/src
WORKDIR /torcedores-app
RUN mvn package

FROM openjdk:11
WORKDIR /torcedores-app
COPY --from=Builder /torcedores-app/target/torcedores-0.0.1.jar .
#ADD target/torcedores-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "torcedores-0.0.1.jar"]
