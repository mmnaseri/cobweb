package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.ValueReader;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.EdgeConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 7:34 PM)
 */
public class DefaultEdgeConditionalExpression<K extends Serializable & Comparable<K>, P extends Vertex<K>> extends AbstractConditionalExpression<K, P> implements EdgeConditionalExpression<K, P> {

    private final String subject;
    private final ValueReader<P, List<Edge<K>>> valueReader;

    public DefaultEdgeConditionalExpression(String subject, ValueReader<P, List<Edge<K>>> valueReader) {
        this.subject = subject;
        this.valueReader = valueReader;
    }

    @Override
    public ConditionalExpression<K, P> matches(Predicate<Edge<K>> predicate) {
        return setConditional(subject + " matches (predicate)", value -> valueReader.read(value).stream().anyMatch(predicate));
    }

}
