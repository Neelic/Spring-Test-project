FROM eclipse-temurin:21
ARG JAR_FILE=target/*.jar
ENV TELEGRAM_TOKEN=1375780501:AAE4A6Rz0BSnIGzeu896OjQnjzsMEG6_uso
RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app
ENTRYPOINT ["java", "-Dbot.token=${TELEGRAM_TOKEN}", "-jar", "/opt/app/japp.jar"]
