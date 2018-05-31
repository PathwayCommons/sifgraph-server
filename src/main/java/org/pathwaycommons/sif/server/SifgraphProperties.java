package org.pathwaycommons.sif.server;

import org.pathwaycommons.sif.model.RelationTypeEnum;
import org.pathwaycommons.sif.util.EdgeAnnotationType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sifgraph")
public class SifgraphProperties {

    private String data; //SIF graph file or url (Pathway Commons Ext. SIF format)
    private EdgeAnnotationType[] annotations;
    private RelationTypeEnum[] relationships;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public EdgeAnnotationType[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(EdgeAnnotationType[] annotations) {
        this.annotations = annotations;
    }

    public RelationTypeEnum[] getRelationships() {
        return relationships;
    }

    public void setRelationships(RelationTypeEnum[] relationships) {
        this.relationships = relationships;
    }
}
