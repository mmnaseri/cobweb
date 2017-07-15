package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 12:11 PM)
 */
public class IdentifierConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> implements ConditionalExpression<K, P> {

    private Conditional<P> conditional;

    public IdentifierConditionalExpression(K key) {
        conditional = new IdentifierConditional<>(key);
    }

    @Override
    public ConditionalExpression<K, P> and(ConditionalExpression<K, P> expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConditionalExpression<K, P> or(ConditionalExpression<K, P> expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConditionalExpression<K, P> andNot(ConditionalExpression<K, P> expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConditionalExpression<K, P> orNot(ConditionalExpression<K, P> expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Conditional<P> getConditional() {
        return conditional;
    }

}
