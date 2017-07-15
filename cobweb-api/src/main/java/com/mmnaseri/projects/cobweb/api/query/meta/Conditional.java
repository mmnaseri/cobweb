package com.mmnaseri.projects.cobweb.api.query.meta;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 3:05 PM)
 */
public interface Conditional<P> {

    boolean test(P value);

    default Conditional<P> and(Conditional<P> other) {
        return Conditionals.and(this, other);
    }

    default Conditional<P> not() {
        return Conditionals.not(this);
    }

    default Conditional<P> or(Conditional<P> other) {
        return Conditionals.or(this, other);
    }

}
