FROM openjdk:8-jre-alpine3.9

WORKDIR /app

COPY target/*.jar /app/carteira-api.jar

EXPOSE 8082

CMD java -XX:+UseContainerSupport -jar carteira-api.jar