package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.ValueReader;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.VertexConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 12:29 AM)
 */
public class DefaultVertexConditionalExpression<K extends Serializable & Comparable<K>, P extends Edge<K>> extends AbstractConditionalExpression<K, P> implements VertexConditionalExpression<K, P> {

    private final String subject;
    private final ValueReader<Edge<K>, Vertex<K>> valueReader;

    public DefaultVertexConditionalExpression(String subject, ValueReader<Edge<K>, Vertex<K>> valueReader) {
        this.subject = subject;
        this.valueReader = valueReader;
    }

    @Override
    public ConditionalExpression<K, P> matches(Conditional<Vertex<K>> predicate) {
        return setConditional(subject + " matches {" + predicate + "}", value -> predicate.test(valueReader.read(value)));
    }

    @Override
    public ConditionalExpression<K, P> matches(ConditionalExpression<K, Vertex<K>> expression) {
        return matches(expression.getConditional());
    }

}
