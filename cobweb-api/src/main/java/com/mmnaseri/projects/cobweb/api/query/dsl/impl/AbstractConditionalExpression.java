package com.mmnaseri.projects.cobweb.api.query.dsl.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.api.query.meta.impl.NamedConditional;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 3:15 PM)
 */
public abstract class AbstractConditionalExpression<K extends Serializable & Comparable<K>, P extends Persistent<K>> implements ConditionalExpression<K, P> {

    private Conditional<P> conditional;

    @Override
    public final Conditional<P> getConditional() {
        return conditional;
    }

    public ConditionalExpression<K, P> setConditional(String name, Conditional<P> conditional) {
        this.conditional = new NamedConditional<>(name, conditional);
        return this;
    }

    @Override
    public ConditionalExpression<K, P> and(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(getConditional()).and(expression);
    }

    @Override
    public ConditionalExpression<K, P> or(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(getConditional()).or(expression);
    }

    @Override
    public ConditionalExpression<K, P> andNot(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(getConditional()).andNot(expression);
    }

    @Override
    public ConditionalExpression<K, P> orNot(ConditionalExpression<K, P> expression) {
        return new SimpleConditionalExpression<>(getConditional()).orNot(expression);
    }

}
