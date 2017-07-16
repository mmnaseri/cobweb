package com.mmnaseri.projects.cobweb.api.graph.impl.domain;

import com.mmnaseri.projects.cobweb.domain.content.Attachment;
import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.content.DocumentProperties;
import com.mmnaseri.projects.cobweb.domain.content.Tag;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:21 PM)
 */
public class LazyDocument<K extends Serializable & Comparable<K>> extends Document<K> {

    private static final long serialVersionUID = -7991955904783606337L;
    private final Document<K> document;
    private final LazyLoader<Set<Tag<K>>> tagLoader;
    private final LazyLoader<Map<String, Attachment<K>>> attachmentLoader;

    public LazyDocument(Document<K> document, LazyLoader<Set<Tag<K>>> tagLoader, LazyLoader<Map<String, Attachment<K>>> attachmentLoader) {
        this.document = document;
        this.tagLoader = tagLoader;
        this.attachmentLoader = attachmentLoader;
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
        final Set<Tag<K>> tags = tagLoader.load();
        setTags(tags);
        return tags;
    }

    @Override
    public void setTags(Set<Tag<K>> tags) {
        document.setTags(tags);
    }

    @Override
    public Map<String, Attachment<K>> getAttachments() {
        return attachmentLoader.load();
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
    public String getLabel() {
        return document.getLabel();
    }

    @Override
    public void setLabel(String label) {
        document.setLabel(label);
    }

    @Override
    public String mnemonic() {
        return document.mnemonic();
    }

    @Override
    public String toString() {
        return document.toString();
    }

}
