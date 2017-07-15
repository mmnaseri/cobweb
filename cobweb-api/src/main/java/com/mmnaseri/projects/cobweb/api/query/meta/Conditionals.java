package com.mmnaseri.projects.cobweb.api.query.meta;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 3:24 PM)
 */
public final class Conditionals {

    public static <P> Conditional<P> or(Conditional<P> first, Conditional<P> second) {
        return value -> first.test(value) || second.test(value);
    }

    public static <P> Conditional<P> and(Conditional<P> first, Conditional<P> second) {
        return value -> first.test(value) && second.test(value);
    }

    public static <P> Conditional<P> not(Conditional<P> conditional) {
        return value -> !conditional.test(value);
    }

}
