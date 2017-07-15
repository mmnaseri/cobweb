package com.mmnaseri.projects.cobweb.api.graph.impl.domain;

import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:25 PM)
 */
public class LazyEdge<K extends Serializable & Comparable<K>> extends Edge<K> implements LazyPersistent {

    private static final long serialVersionUID = -4807540548814226339L;
    private final Edge<K> edge;
    private final LazyDocument<K> document;

    public LazyEdge(Edge<K> edge, LazyLoader<Set<Tag<K>>> tagLoader, LazyLoader<Map<String, Attachment<K>>> attachmentLoader) {
        this.edge = edge;
        document = new LazyDocument<>(edge, tagLoader, attachmentLoader);
    }

    @Override
    public Identifier<K> getId() {
        return document.getId();
    }

    @Override
    public void setId(Identifier<K> id) {
        document.setId(id);
    }

    @Override
    public Set<Tag<K>> getTags() {
        return document.getTags();
    }

    @Override
    public void setTags(Set<Tag<K>> tags) {
        document.setTags(tags);
    }

    @Override
    public Map<String, Attachment<K>> getAttachments() {
        return document.getAttachments();
    }

    @Override
    public void setAttachments(Map<String, Attachment<K>> attachments) {
        document.setAttachments(attachments);
    }

    @Override
    public DocumentProperties getProperties() {
        return document.getProperties();
    }

    @Override
    public void setProperties(DocumentProperties properties) {
        document.setProperties(properties);
    }

    @Override
    public Vertex<K> getFrom() {
        return edge.getFrom();
    }

    @Override
    public void setFrom(Vertex<K> from) {
        edge.setFrom(from);
    }

    @Override
    public Vertex<K> getTo() {
        return edge.getTo();
    }

    @Override
    public void setTo(Vertex<K> to) {
        edge.setTo(to);
    }

    @Override
    public String getLabel() {
        return document.getLabel();
    }

    @Override
    public void setLabel(String label) {
        document.setLabel(label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LazyEdge<?> lazyEdge = (LazyEdge<?>) o;
        return edge.equals(lazyEdge.edge);
    }

    @Override
    public int hashCode() {
        return edge.hashCode();
    }

    @Override
    public String toString() {
        return edge.toString();
    }

}
