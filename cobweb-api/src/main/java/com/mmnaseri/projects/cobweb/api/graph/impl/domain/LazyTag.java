package com.mmnaseri.projects.cobweb.api.graph.impl.domain;

import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.content.Tag;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:11 PM)
 */
public class LazyTag<K extends Serializable & Comparable<K>> extends Tag<K> implements LazyPersistent {

    private static final long serialVersionUID = 7127175717661366044L;
    private final Tag<K> tag;
    private final LazyLoader<List<Document<K>>> documentLoader;

    public LazyTag(Tag<K> tag, LazyLoader<List<Document<K>>> documentLoader) {
        this.tag = tag;
        this.documentLoader = documentLoader;
    }

    @Override
    public Identifier<K> getId() {
        return tag.getId();
    }

    @Override
    public String getName() {
        return tag.getName();
    }

    @Override
    public void setId(Identifier<K> id) {
        tag.setId(id);
    }

    @Override
    public void setName(String name) {
        tag.setName(name);
    }

    @Override
    public String getDescription() {
        return tag.getDescription();
    }

    @Override
    public void setDescription(String description) {
        tag.setDescription(description);
    }

    @Override
    public List<Document<K>> getDocuments() {
        return documentLoader.load();
    }

    @Override
    public void setDocuments(List<Document<K>> documents) {
        tag.setDocuments(documents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LazyTag<?> lazyTag = (LazyTag<?>) o;

        return tag.equals(lazyTag.tag);
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

}
