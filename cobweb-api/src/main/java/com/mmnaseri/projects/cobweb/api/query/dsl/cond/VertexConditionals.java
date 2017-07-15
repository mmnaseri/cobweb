package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl.DefaultEdgeConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 7:33 PM)
 */
public final class VertexConditionals {

    private VertexConditionals() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>, P extends Vertex<K>> EdgeConditionalExpression<K, P> incomingEdges(Sources<K, P> sources) {
        return new DefaultEdgeConditionalExpression<>("vertex.incomingEdges", Vertex::getIncoming);
    }

}
