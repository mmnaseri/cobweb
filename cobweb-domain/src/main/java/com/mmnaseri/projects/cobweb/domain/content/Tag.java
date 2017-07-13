package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:17 PM)
 */
public class Tag<I extends Serializable & Comparable<I>> extends Persistent<I> {

    private static final long serialVersionUID = 2980693694492421432L;

    private String name;
    private String description;
    private List<Document<I>> documents;

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

    public List<Document<I>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document<I>> documents) {
        this.documents = documents;
    }

}
