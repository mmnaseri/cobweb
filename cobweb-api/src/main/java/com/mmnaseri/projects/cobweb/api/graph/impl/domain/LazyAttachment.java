package com.mmnaseri.projects.cobweb.api.graph.impl.domain;

import com.mmnaseri.projects.cobweb.domain.content.Attachment;
import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:15 PM)
 */
public class LazyAttachment<K extends Serializable & Comparable<K>> extends Attachment<K> implements LazyPersistent {

    private static final long serialVersionUID = 1185267914697537573L;
    private final Attachment<K> attachment;
    private final LazyLoader<Set<Document<K>>> anchorLoader;

    public LazyAttachment(Attachment<K> attachment, LazyLoader<Set<Document<K>>> anchorLoader) {
        this.attachment = attachment;
        this.anchorLoader = anchorLoader;
    }

    @Override
    public String getMime() {
        return attachment.getMime();
    }

    @Override
    public Identifier<K> getId() {
        return attachment.getId();
    }

    @Override
    public void setMime(String mime) {
        attachment.setMime(mime);
    }

    @Override
    public void setId(Identifier<K> id) {
        attachment.setId(id);
    }

    @Override
    public Set<Document<K>> getAnchors() {
        return anchorLoader.load();
    }

    @Override
    public void setAnchors(Set<Document<K>> anchors) {
        attachment.setAnchors(anchors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LazyAttachment<?> that = (LazyAttachment<?>) o;
        return attachment.equals(that.attachment);
    }

    @Override
    public int hashCode() {
        return attachment.hashCode();
    }

}
