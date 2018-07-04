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
java -Xmx16g -jar build/libs/sifgraph-server.jar --sifgraph.data="file:path/to/graph.txt.gz" --server.port=8080
```

Note: 

Use `--sifgraph.relationships=...` (see: src/main/resources/config/application.properties, -
which you'd either modify there before building the app or simply copy and edit it in the current directory, 
where the above command gets executed.)

RESTful API (Swagger docs):

Once the app is built and running, 
the auto-generated documentation is available at 
`http://localhost:8080/sifgraph/swagger-ui.html`
