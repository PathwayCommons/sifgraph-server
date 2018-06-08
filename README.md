# Pathway Commons SIF Graph Query Server

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone, then build & run:
```
./gradlew build 
java -Xmx16g -jar build/libs/sifgraph-server.jar
```

The web service docs are then available at `http://localhost:8080/sifgraph/swagger-ui.html`
