package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:18 PM)
 */
public class Edge<I extends Serializable & Comparable<I>> extends Document<I> {

    private static final long serialVersionUID = 9075813582421628901L;
    private Vertex<I> from;
    private Vertex<I> to;

    public Vertex<I> getFrom() {
        return from;
    }

    public void setFrom(Vertex<I> from) {
        this.from = from;
    }

    public Vertex<I> getTo() {
        return to;
    }

    public void setTo(Vertex<I> to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "{edge(" + getLabel() + ")}";
    }

}
