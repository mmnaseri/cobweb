package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:12 PM)
 */
public abstract class Document<I extends Serializable & Comparable<I>> extends Persistent<I> {

    private static final long serialVersionUID = -2921615765012084346L;
    private Set<Tag<I>> tags;
    private Map<String, Attachment<I>> attachments;
    private DocumentProperties properties;
    private String label;

    public Set<Tag<I>> getTags() {
        return tags;
    }

    public void setTags(Set<Tag<I>> tags) {
        this.tags = tags;
    }

    public Map<String, Attachment<I>> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Attachment<I>> attachments) {
        this.attachments = attachments;
    }

    public DocumentProperties getProperties() {
        return properties;
    }

    public void setProperties(DocumentProperties properties) {
        this.properties = properties;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public abstract String mnemonic();

    @Override
    public String toString() {
        return "{" + mnemonic() + "(" + getLabel() + ")[" +
                (getTags() == null ? Collections.<Tag<I>>emptySet() : getTags()).stream().map(tag -> "#" + tag.getName()).reduce((a, b) -> a + "," + b).orElse("")
                + "]}";
    }

}
