package com.mmnaseri.projects.cobweb.domain.content;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:18 PM)
 */
public class Vertex<I extends Serializable & Comparable<I>> extends Document<I> {

    private static final long serialVersionUID = 6038699349833770390L;

    private List<Edge<I>> incoming;
    private List<Edge<I>> outgoing;

    public List<Edge<I>> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<Edge<I>> incoming) {
        this.incoming = incoming;
    }

    public List<Edge<I>> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(List<Edge<I>> outgoing) {
        this.outgoing = outgoing;
    }

    @Override
    public String toString() {
        return "{vertex(" + getLabel() + ")}";
    }

}
