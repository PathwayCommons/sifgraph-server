# Pathway Commons graph query server

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone, then build & run:

./gradlew build && java -Dserver.port=8080 -jar build/libs/sifgraph-server.jar
