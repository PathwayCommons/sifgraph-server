FROM eclipse-temurin:latest
VOLUME /tmp
ENV SIFGRAPH_DATA="classpath:bmp.gz"
COPY build/libs/sifgraph-server-1.0.jar app.jar
ENTRYPOINT ["java","-Xmx16g","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8080
