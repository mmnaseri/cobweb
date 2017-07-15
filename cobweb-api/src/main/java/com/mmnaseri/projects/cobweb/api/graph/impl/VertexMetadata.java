package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.domain.content.DocumentProperties;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;
import java.util.Collections;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 7:24 PM)
 */
public class VertexMetadata implements Serializable {

    private static final long serialVersionUID = 4284805994781567890L;
    private String label;

    public VertexMetadata() {
    }

    public VertexMetadata(Vertex<?> vertex) {
        this.label = vertex.getLabel();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public <K extends Serializable & Comparable<K>> Vertex<K> toVertex(K key, IdentifierFactory<K> identifierFactory) {
        final Vertex<K> vertex = new Vertex<>();
        vertex.setId(identifierFactory.getIdentifier(key));
        vertex.setLabel(getLabel());
        vertex.setProperties(new DocumentProperties());
        vertex.setIncoming(Collections.emptyList());
        vertex.setOutgoing(Collections.emptyList());
        vertex.setAttachments(Collections.emptyMap());
        vertex.setTags(Collections.emptySet());
        return vertex;
    }

}
