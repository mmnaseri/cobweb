package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.ValueConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.ValueReader;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:27 PM)
 */
public class DefaultValueConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>, V> extends AbstractConditionalExpression<K, P> implements ValueConditionalExpression<K, P, V> {

    private final String subject;
    private final ValueReader<P, V> valueReader;

    public DefaultValueConditionalExpression(String subject, ValueReader<P, V> valueReader) {
        this.subject = subject;
        this.valueReader = valueReader;
    }

    @Override
    public ConditionalExpression<K, P> is(V value) {
        return setConditional(subject + " == " + value, p -> valueReader.read(p).equals(value));
    }

    @Override
    public ConditionalExpression<K, P> isNot(V value) {
        return setConditional(subject + " != " + value, p -> !valueReader.read(p).equals(value));
    }

}
