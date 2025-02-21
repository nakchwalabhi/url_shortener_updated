FROM openjdk:23 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:23-slim
COPY --from=build /target/url_shortner_updated-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT [ "java","jar","demo.jar" ]
