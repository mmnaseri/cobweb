package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.query.meta.ProjectionSpecification;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:48 PM)
 */
public final class Projections {

    private Projections() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>, P extends Persistent<K>> ProjectionSpecification<P, P> itself() {
        return p -> p;
    }

}
