package org.pathwaycommons.sif.server;

import org.pathwaycommons.sif.io.Loader;
import org.pathwaycommons.sif.model.SIFGraph;
import org.pathwaycommons.sif.query.Direction;
import org.pathwaycommons.sif.query.QueryExecutor;
import org.pathwaycommons.sif.util.EdgeAnnotationType;
import org.pathwaycommons.sif.util.EdgeSelector;
import org.pathwaycommons.sif.util.RelationTypeSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;


@RestController
public class Controller {

    private SIFGraph graph;
    private EdgeSelector edgeSelector;
    private org.pathwaycommons.sif.io.Writer sifWriter;
    private SifgraphProperties props;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    public Controller(SifgraphProperties properties) {
        this.props = properties;
    }

    @PostConstruct
    void init() throws IOException {
        final EdgeAnnotationType[] annotationTypes = props.getAnnotations();
        edgeSelector = new RelationTypeSelector(props.getRelationships());
        sifWriter = new org.pathwaycommons.sif.io.Writer(false, annotationTypes);
        InputStream is = resourceLoader.getResource(props.getData()).getInputStream();
        graph = new Loader(annotationTypes).load(new GZIPInputStream(is));
    }

    @RequestMapping(path = "/neighborhood", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String nhood (
        @RequestParam(defaultValue = "BOTHSTREAM") Direction direction,
//        @RequestParam(defaultValue = "1") Integer limit,
        @RequestParam String[] source
    )
    {
        Set<String> sources =  new HashSet();
        for(String s : source)
            sources.add(s);
        Set<Object> result = QueryExecutor.searchNeighborhood(graph, edgeSelector, sources, direction, 1);

        return write(result);
    }

    @RequestMapping(path = "/pathsbetween", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String pathsbetween (
        @RequestParam(defaultValue = "false") Boolean directed,
        @RequestParam(defaultValue = "1") Integer limit,
        @RequestParam String[] source
    )
    {
        Set<String> sources =  new HashSet();
        for(String s : source)
            sources.add(s);

        if(limit > 3) limit = 1; //too much data or out of memory when limit > 3

        Set<Object> result = QueryExecutor.searchPathsBetween(graph, edgeSelector, sources, directed, limit);

        return write(result);
    }

    @RequestMapping(path = "/commonstream", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String commonstream (
        @RequestParam(defaultValue = "DOWNSTREAM") Direction direction,
//        @RequestParam(defaultValue = "1") Integer limit,
        @RequestParam String[] source
    )
    {
        Set<String> sources =  new HashSet();
        for(String s : source)
            sources.add(s);
        Set<Object> result = QueryExecutor.searchCommonStream(graph, edgeSelector, sources, direction, 1);

        return write(result);
    }

    @RequestMapping(path = "/pathsfromto", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String pathsfromto (
        @RequestParam(defaultValue = "1") Integer limit,
        @RequestParam String[] source,
        @RequestParam String[] target
    )
    {
        Set<String> sources =  new HashSet();
        for(String s : source)
            sources.add(s);
        Set<String> targets =  new HashSet();
        for(String s : target)
            targets.add(s);

        if(limit > 3) limit = 1; //too much data or out of memory when limit > 3

        Set<Object> result = QueryExecutor.searchPathsFromTo(graph, edgeSelector, sources, targets, limit);

        return write(result);
    }

    private String write(Set<Object> result) {
        OutputStream bos  = new ByteArrayOutputStream();
        try {
            sifWriter.write(result, bos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write result to text format", e);
        }

        return bos.toString();
    }

}
