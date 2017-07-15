package com.mmnaseri.projects.cobweb.api.graph.impl.domain;

import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:17 PM)
 */
public class LazyVertex<K extends Serializable & Comparable<K>> extends Vertex<K> implements LazyPersistent {

    private static final long serialVersionUID = 8834152754070341251L;
    private final Vertex<K> vertex;
    private final LazyLoader<List<Edge<K>>> incomingLoader;
    private final LazyLoader<List<Edge<K>>> outgoingLoader;
    private final LazyDocument<K> document;

    public LazyVertex(Vertex<K> vertex, LazyLoader<Set<Tag<K>>> tagLoader, LazyLoader<Map<String, Attachment<K>>> attachmentLoader, LazyLoader<List<Edge<K>>> incomingLoader, LazyLoader<List<Edge<K>>> outgoingLoader) {
        this.vertex = vertex;
        this.incomingLoader = incomingLoader;
        this.outgoingLoader = outgoingLoader;
        document = new LazyDocument<K>(vertex, tagLoader, attachmentLoader);
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
    public List<Edge<K>> getIncoming() {
        return incomingLoader.load();
    }

    @Override
    public void setIncoming(List<Edge<K>> incoming) {
        vertex.setIncoming(incoming);
    }

    @Override
    public List<Edge<K>> getOutgoing() {
        return outgoingLoader.load();
    }

    @Override
    public void setOutgoing(List<Edge<K>> outgoing) {
        vertex.setOutgoing(outgoing);
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
        LazyVertex<?> that = (LazyVertex<?>) o;
        return vertex.equals(that.vertex);
    }

    @Override
    public int hashCode() {
        return vertex.hashCode();
    }

    @Override
    public String toString() {
        return vertex.toString();
    }

}
