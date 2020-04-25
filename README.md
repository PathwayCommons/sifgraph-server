# Pathway Commons SIF Graph Query Server

[![Build Status](https://travis-ci.org/PathwayCommons/sifgraph-server.svg?branch=master)](https://travis-ci.org/PathwayCommons/sifgraph-server) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/edad8a3d679c4d99b608440dc6d397a8)](https://www.codacy.com/app/IgorRodchenkov/sifgraph-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=PathwayCommons/sifgraph-server&amp;utm_campaign=Badge_Grade)

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone the repository.

See `src/main/resources/config/application.properties` comments; 
download a Pathway Commons Extended SIF data archive and save it as data.txt.gz in this project directory, e.g.:

```commandline
wget -O data.txt.gz "https://www.pathwaycommons.org/archives/PC2/v12/PathwayComons12.All.hgnc.txt.gz"
```

(alternatively, set `--sifgraph.data=...`, etc., options at 
the end of the `java -jar` command (see below), or set `SIFGRAPH_DATA` system variable.)

Build:

```commandline
./gradlew build
```

Run:

```commandline
java -Xmx8g -jar build/libs/sifgraph-server*.jar
```

Note: override the default list of SIF patterns using `--sifgraph.relationships=...` if needed (depends on the data).

If you want to run __different instances__ of the graph server, e.g., for different species, then  
copy src/main/resources/config/application.properties to the work directory, 
rename (e.g., my.properties), modify (e.g., set another `server.port`, `sifgraph.relationships` 
and `sifgraph.data` file), and run as:

```commandline
java -Xmx8g -jar build/libs/sifgraph-server*.jar --spring.config.name=my
```

or

```commandline
java -Xmx8g  -jar build/libs/sifgraph-server*.jar --sifgraph.data="file:<other.file>" --server.port=<otherPort>
```


RESTful API (Swagger docs):

Once the app is built and running, 
the auto-generated documentation is available at 
`http://localhost:8080/sifgraph/`


## Docker
You can also build and run the docker image as follows   
(`<PORT>` - actual port number where the server will run; data file download step above is also required). 

```commandline
./gradlew build
docker build . -t pathwaycommons/sifgraph-server 
docker run -it --rm --name sifgraph-server -p <PORT>:8080 pathwaycommons/sifgraph-server 
```

Optionally, (a member of 'pathwaycommons' group) can now push the latest Docker image there:

```commandline
docker login
docker push pathwaycommons/sifgraph-server
```  

So, other users could skip building from sources and simply run the app:
```commandline
docker pull
docker run -p <PORT>:8080 -t pathwaycommons/sifgraph-server
```

(you can `Ctrl-c` and quit the console; the container is still there running; check with `docker ps`)
