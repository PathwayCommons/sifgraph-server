# Pathway Commons SIF Graph Query Server

[![gradle](https://github.com/PathwayCommons/sifgraph-server/actions/workflows/gradle.yml/badge.svg)](https://github.com/PathwayCommons/sifgraph-server/actions/workflows/gradle.yml)

A Spring Boot RESTful web service built around [sifgraph](https://github.com/PathwayCommons/sifgraph) 
java library that executes _neighborhood_, _pathsbetween_, etc., graph queries 
on the Pathway Commons' [Extended SIF](http://www.pathwaycommons.org/pc2/formats#sif) data.

Clone the repository.

Build:

```commandline
./gradlew build
```

Get data:

See comments in: `src/main/resources/config/application.properties`; 

Download a Pathway Commons Extended SIF data archive and save it as data.gz in current dir:

```commandline
wget -O data.gz "https://www.pathwaycommons.org/archives/PC2/v12/PathwayComons12.All.hgnc.txt.gz"
```

Alternatively, can use the remote URL in the `--sifgraph.data=https://...` option  
for the `java` command below, or set via `SIFGRAPH_DATA` system/environment property instead.

Run:

```commandline
$JAVA_HOME/bin/java -Xmx8g -jar build/libs/sifgraph-server*.jar --sifgraph.data=file:data.gz
```

Note: override the default list of SIF patterns using `--sifgraph.relationships=...` if needed (depends on the data).

If you want to run __different instances__ of the graph server, e.g., for different species, then  
copy src/main/resources/config/application.properties to the work directory, 
rename (e.g., my.properties), modify (e.g., set another `server.port`, `sifgraph.relationships` 
and `sifgraph.data` file), and run as:

```commandline
java -Xmx8g -jar build/libs/sifgraph-server*.jar --spring.config.name=my
```

or simply provide all the different options via the args or env properties:

```commandline
java -Xmx8g  -jar build/libs/sifgraph-server*.jar --sifgraph.data="file:<other.file>" --server.port=<otherPort>
```


RESTful API (Swagger docs):

Once the app is built and running, 
the auto-generated documentation is available at 
`http://localhost:8080/sifgraph/`


## Docker
You can also build and run the docker image as follows 

You need to pass `SIFGRAPH_DATA` URL and (if `file:`) mount a local file to the container FS as 
read-only volume (or the app will load test/demo data from its classpath by default).


```commandline
./gradlew build
docker build . -t pathwaycommons/sifgraph-server 
docker run -it --rm --name sifgraph-server -p <PORT>:8080 pathwaycommons/sifgraph-server 
```

Optionally, a member of 'pathwaycommons' group can now push the latest Docker image to hub.docker.com:

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
