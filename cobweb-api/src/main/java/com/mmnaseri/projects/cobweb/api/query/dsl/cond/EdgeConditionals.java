package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl.DefaultVertexConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Edge;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 5:48 PM)
 */
public final class EdgeConditionals {

    private EdgeConditionals() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>, P extends Edge<K>> VertexConditionalExpression<K, P> fromVertex(Sources<K, P> sources) {
        return new DefaultVertexConditionalExpression<>("from", Edge::getFrom);
    }

    public static <K extends Serializable & Comparable<K>, P extends Edge<K>> VertexConditionalExpression<K, P> toVertex(Sources<K, P> sources) {
        return new DefaultVertexConditionalExpression<>("to", Edge::getTo);
    }


}
