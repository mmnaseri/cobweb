package com.mmnaseri.projects.cobweb.api.query.meta.impl;

import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditionals;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:12 PM)
 */
public class NamedConditional<P> implements Conditional<P> {

    private final Conditional<P> conditional;
    private final String name;

    public NamedConditional(String name, Conditional<P> conditional) {
        this.conditional = conditional;
        this.name = name;
    }

    @Override
    public boolean test(P value) {
        return conditional.test(value);
    }

    public String getName() {
        return name;
    }

    @Override
    public NamedConditional<P> and(Conditional<P> other) {
        return new NamedConditional<>("(" + getName() + ") && (" + getOtherName(other) + ")", value -> Conditionals.and(this, other).test(value));
    }

    @Override
    public NamedConditional<P> not() {
        return new NamedConditional<>("!(" + getName() + ")", value -> Conditionals.not(this).test(value));
    }

    @Override
    public NamedConditional<P> or(Conditional<P> other) {
        return new NamedConditional<>("(" + getName() + ") || (" + getOtherName(other) + ")", value -> Conditionals.or(this, other).test(value));
    }

    @Override
    public String toString() {
        return getName();
    }

    private String getOtherName(Conditional<P> other) {
        return other instanceof NamedConditional ? ((NamedConditional) other).getName() : "(unnamed condition)";
    }

}
