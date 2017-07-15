package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:26 PM)
 */
public interface ValueConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>, V> extends ConditionalExpression<K, P> {

    ConditionalExpression<K, P> is(V value);

    ConditionalExpression<K, P> isNot(V value);

}
