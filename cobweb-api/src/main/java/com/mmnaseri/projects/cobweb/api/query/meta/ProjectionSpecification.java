package com.mmnaseri.projects.cobweb.api.query.meta;

import java.util.function.Function;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:43 PM)
 */
public interface ProjectionSpecification<S, R> extends Function<S, R> {

    default R project(S source) {
        return apply(source);
    }

}
