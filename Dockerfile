FROM openjdk:11

COPY ./target/maptypro-0.0.1-SNAPSHOT.jar /app.jar

#CMD ["--server.port=5000"]

EXPOSE 5000

CMD ["java","-jar","/app.jar"]