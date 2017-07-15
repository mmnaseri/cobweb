package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.domain.content.Attachment;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Collections;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:58 PM)
 */
public class AttachmentMetadata implements Serializable {

    private static final long serialVersionUID = 2368772334330013399L;
    private String mime;

    public AttachmentMetadata() {
    }

    public AttachmentMetadata(Attachment<?> attachment) {
        this.mime = attachment.getMime();
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public <K extends Serializable & Comparable<K>> Attachment<K> toAttachment(Identifier<K> key) {
        final Attachment<K> attachment = new Attachment<>();
        attachment.setId(key);
        attachment.setMime(getMime());
        attachment.setAnchors(Collections.emptySet());
        return attachment;
    }

}
