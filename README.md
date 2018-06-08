# Pathway Commons SIF Graph Query Server

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone the repository.

See `src/main/resources/config/application.properties` comments; 
download the extended SIF data file and update the properties accordingly 
(alternatively, set `--sifgraph.data=...`, etc., options at 
the end of the `java -jar` command (see below), or set `SIFGRAPH_DATA` system variable.)

Build:
```
./gradlew build 
```

Run:

```
java -Xmx16g -jar build/libs/sifgraph-server.jar --sifgraph.data="path/to/graph.txt.gz"
```

The web service docs are then available at `http://localhost:8080/sifgraph/swagger-ui.html`
