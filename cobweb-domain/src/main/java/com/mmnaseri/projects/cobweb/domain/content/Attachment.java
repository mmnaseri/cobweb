package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:11 PM)
 */
public class Attachment<I extends Serializable & Comparable<I>> extends Persistent<I> {

    private static final long serialVersionUID = 2149521879563062235L;
    private String mime;
    private Set<Document<I>> anchors;

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Set<Document<I>> getAnchors() {
        return anchors;
    }

    public void setAnchors(Set<Document<I>> anchors) {
        this.anchors = anchors;
    }

    @Override
    public String toString() {
        return "{attachment(" + getMime() + ")}";
    }

}
