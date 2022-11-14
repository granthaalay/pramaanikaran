FROM openjdk:17.0.4
COPY build/libs/pramaanikaran-0.0.1.jar pramaanikaran-0.0.1.jar
ENTRYPOINT ["java", "-jar", "pramaanikaran-0.0.1.jar"]