package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:51 PM)
 */
public interface ConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> {

    ConditionalExpression<K, P> and(ConditionalExpression<K, P> expression);

    ConditionalExpression<K, P> or(ConditionalExpression<K, P> expression);

    ConditionalExpression<K, P> andNot(ConditionalExpression<K, P> expression);

    ConditionalExpression<K, P> orNot(ConditionalExpression<K, P> expression);

    Conditional<P> getConditional();

}
