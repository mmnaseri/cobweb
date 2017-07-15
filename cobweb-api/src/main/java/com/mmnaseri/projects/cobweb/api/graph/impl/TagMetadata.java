package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.domain.content.Tag;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.Collections;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:57 PM)
 */
public class TagMetadata implements Serializable {

    private static final long serialVersionUID = -2893563657138184538L;
    private String name;
    private String description;

    public TagMetadata() {
    }

    public TagMetadata(Tag<?> tag) {
        this.name = tag.getName();
        this.description = tag.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public <K extends Serializable & Comparable<K>> Tag<K> toTag(Identifier<K> key) {
        final Tag<K> tag = new Tag<>();
        tag.setId(key);
        tag.setName(getName());
        tag.setDescription(getDescription());
        tag.setDocuments(Collections.emptyList());
        return tag;
    }

}
