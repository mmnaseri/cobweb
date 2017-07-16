package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 7:32 PM)
 */
public interface VertexConditionalExpression<K extends Serializable & Comparable<K>, P extends Edge<K>> extends ConditionalExpression<K, P> {

    ConditionalExpression<K, P> matches(Conditional<Vertex<K>> predicate);

    ConditionalExpression<K, P> matches(ConditionalExpression<K, Vertex<K>> expression);

}
