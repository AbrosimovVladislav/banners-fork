FROM openjdk:17-jdk-alpine
#FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY back/target/ai-banners-generator-0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]