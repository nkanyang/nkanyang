FROM openjdk:14.0
VOLUME /tmp
EXPOSE 8080
COPY target/*.jar app.jar
COPY products.db products.db
COPY src/main/resources/application.production.properties application.properties
ENTRYPOINT ["java","-jar","-Dspring.config.location=application.properties","/app.jar"]