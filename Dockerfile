FROM eclipse-temurin:17-jre-jammy

LABEL author="azure99"
LABEL mail="i@rainng.com"

WORKDIR /app

COPY target/alg-contest-info.jar /app/

ENTRYPOINT ["java","-jar","alg-contest-info.jar"]