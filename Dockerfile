FROM openjdk:11 AS Builder
RUN apt-get update
RUN apt-get install -y maven
COPY ./api/pom.xml /torcedores-app/pom.xml
COPY ./api/src /torcedores-app/src
WORKDIR /torcedores-app
RUN mvn package -DskipTests=true

FROM openjdk:11
WORKDIR /torcedores-app
COPY --from=Builder /torcedores-app/target/torcedores-0.0.1.jar .
#ADD target/torcedores-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "torcedores-0.0.1.jar"]
