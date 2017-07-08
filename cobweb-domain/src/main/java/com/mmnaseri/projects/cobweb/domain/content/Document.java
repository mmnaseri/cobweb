package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:12 PM)
 */
public abstract class Document<I extends Serializable> extends Persistent<I> {

    private static final long serialVersionUID = -2921615765012084346L;
    private Set<Tag<I>> tags;
    private Map<String, Attachment<I>> attachments;

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

    @Override
    public ObjectType getObjectType() {
        return ObjectType.DOCUMENT;
    }

}
