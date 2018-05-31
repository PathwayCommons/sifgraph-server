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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("/neighborhood")
    public String nhood (
        @RequestParam(defaultValue = "BOTHSTREAM") Direction direction,
        @RequestParam(defaultValue = "1") Integer limit,
        @RequestParam String[] source //, HttpServletResponse response
    ) throws IOException
    {
        Set<String> sources =  new HashSet();
        for(String s : source)
            sources.add(s);
        Set<Object> result = QueryExecutor.searchNeighborhood(graph, edgeSelector, sources, direction, limit);
        OutputStream bos  = new ByteArrayOutputStream();
        sifWriter.write(result, bos);
//        sifWriter.write(result, response.getOutputStream());
        return bos.toString();
    }
}
