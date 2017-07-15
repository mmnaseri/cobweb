package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 7:32 PM)
 */
public interface EdgeConditionalExpression<K extends Serializable & Comparable<K>, P extends Vertex<K>> extends ConditionalExpression<K, P> {

    ConditionalExpression<K, P> matches(Predicate<Edge<K>> predicate);

}
