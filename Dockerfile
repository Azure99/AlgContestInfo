FROM openjdk:11

LABEL author="azure99"
LABEL mail="i@rainng.com"

USER root
WORKDIR /root

COPY target/alg-contest-info.jar /root/

ENTRYPOINT java -jar alg-contest-info.jar