# Pathway Commons SIF Graph Query Server

[![Build Status](https://travis-ci.org/PathwayCommons/sifgraph-server.svg?branch=master)](https://travis-ci.org/PathwayCommons/sifgraph-server) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/edad8a3d679c4d99b608440dc6d397a8)](https://www.codacy.com/app/IgorRodchenkov/sifgraph-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=PathwayCommons/sifgraph-server&amp;utm_campaign=Badge_Grade)

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone the repository.

See `src/main/resources/config/application.properties` comments; 
download the extended SIF data file and update the properties accordingly 
(alternatively, set `--sifgraph.data=...`, etc., options at 
the end of the `java -jar` command (see below), or set `SIFGRAPH_DATA` system variable.)

Build:

```commandline
./gradlew build
```

Run:

```commandline
java -jar build/libs/sifgraph-server-0.3.0.jar -Xmx4g --sifgraph.data="file:/path/to/graph.txt.gz" --server.port=8080
```

Note: override the default list of SIF patterns using `--sifgraph.relationships=...` if needed (depends on the data).

If you want to run __different instances__ of the graph server, e.g., for different species, then simply 
copy src/main/resources/config/application.properties to the work/current directory, 
rename (e.g., my.properties), modify (e.g., set another `server.port`, `sifgraph.relationships` 
and `sifgraph.data` file), and run as:

```commandline
java -jar build/libs/sifgraph-server.jar --spring.config.name=my
```

RESTful API (Swagger docs):

Once the app is built and running, 
the auto-generated documentation is available at 
`http://localhost:8080/sifgraph/`
