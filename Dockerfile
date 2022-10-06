FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE="build/libs/sifgraph-server*.jar"
ARG DATA_FILE="data.txt.gz"
ENV DATA_URL="file:${DATA_FILE}"
COPY ${JAR_FILE} app.jar
COPY ${DATA_FILE} .
ENTRYPOINT ["java","-Xmx16g","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--sifgraph.data=${DATA_URL}"]
EXPOSE 8080
