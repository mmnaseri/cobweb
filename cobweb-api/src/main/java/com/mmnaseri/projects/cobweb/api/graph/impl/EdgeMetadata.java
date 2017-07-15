package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.domain.content.DocumentProperties;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 5:47 PM)
 */
public class EdgeMetadata<K extends Serializable & Comparable<K>> implements Serializable {

    private static final long serialVersionUID = 4681949030550724791L;
    private K from;
    private K to;
    private DocumentProperties properties;

    public EdgeMetadata() {
    }

    public EdgeMetadata(Edge<K> edge) {
        this.from = edge.getFrom().getId().getValue();
        this.to = edge.getTo().getId().getValue();
        this.properties = edge.getProperties();
    }

    public K getFrom() {
        return from;
    }

    public void setFrom(K from) {
        this.from = from;
    }

    public K getTo() {
        return to;
    }

    public void setTo(K to) {
        this.to = to;
    }

    public DocumentProperties getProperties() {
        return properties;
    }

    public void setProperties(DocumentProperties properties) {
        this.properties = properties;
    }

    public Edge<K> toEdge(K key, IdentifierFactory<K> factory) {
        final Edge<K> edge = new Edge<>();
        edge.setId(factory.getIdentifier(key));
        final Vertex<K> from = new Vertex<>();
        from.setId(factory.getIdentifier(getFrom()));
        final Vertex<K> to = new Vertex<>();
        to.setId(factory.getIdentifier(getTo()));
        edge.setId(factory.getIdentifier(key));
        edge.setFrom(from);
        edge.setTo(to);
        edge.setProperties(getProperties());
        return edge;
    }

}
