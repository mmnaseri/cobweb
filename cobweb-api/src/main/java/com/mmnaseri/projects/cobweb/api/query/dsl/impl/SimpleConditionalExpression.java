package com.mmnaseri.projects.cobweb.api.query.dsl.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 3:07 PM)
 */
public class SimpleConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> implements ConditionalExpression<K, P> {

    private final Conditional<P> conditional;

    public SimpleConditionalExpression(Conditional<P> conditional) {
        this.conditional = conditional;
    }

    @Override
    public ConditionalExpression<K, P> and(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(conditional.and(expression.getConditional()));
    }

    @Override
    public ConditionalExpression<K, P> or(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(conditional.or(expression.getConditional()));
    }

    @Override
    public ConditionalExpression<K, P> andNot(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(conditional.and(expression.getConditional().not()));
    }

    @Override
    public ConditionalExpression<K, P> orNot(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(conditional.or(expression.getConditional().not()));
    }

    @Override
    public Conditional<P> getConditional() {
        return conditional;
    }

}
