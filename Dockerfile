FROM openjdk:14-alpine
COPY build/libs/micronaut-demo-*-all.jar micronaut-demo.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-demo.jar"]