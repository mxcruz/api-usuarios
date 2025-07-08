FROM openjdk:21-slim

COPY target/api-usuarios_*.jar bff.jar

ENTRYPOINT ["java","-jar","/bff.jar"]