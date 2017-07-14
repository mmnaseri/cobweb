package com.mmnaseri.projects.cobweb.api.query;

import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.api.query.meta.ProjectionSpecification;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:29 PM)
 */
public interface Query<S, R> {

    Conditional<S> getConditional();

    ProjectionSpecification<S, R> getProjectionSpecification();

}
