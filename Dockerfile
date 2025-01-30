FROM openjdk:21-jdk

WORKDIR /app

COPY target/ecommerce-remake-1.0.jar /app/ecommerce-remake.jar

EXPOSE 8080

CMD ["java", "-jar", "ecommerce-remake.jar"]
