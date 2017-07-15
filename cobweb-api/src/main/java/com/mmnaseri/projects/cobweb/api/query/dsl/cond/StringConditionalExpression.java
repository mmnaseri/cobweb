package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:54 PM)
 */
public interface StringConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> extends ConditionalExpression<K, P> {

    ConditionalExpression<K, P> is(String value);

    ConditionalExpression<K, P> isNot(String value);

    ConditionalExpression<K, P> isIgnoreCase(String value);

    ConditionalExpression<K, P> isNotIgnoreCase(String value);

    ConditionalExpression<K, P> contains(String needle);

    ConditionalExpression<K, P> doesNotContain(String needle);

    ConditionalExpression<K, P> matches(String pattern);

    ConditionalExpression<K, P> doesNotMatch(String pattern);

    ConditionalExpression<K, P> startsWith(String prefix);

    ConditionalExpression<K, P> doesNotStartWith(String prefix);

    ConditionalExpression<K, P> endsWith(String suffix);

    ConditionalExpression<K, P> doesNotEndWith(String suffix);

}
