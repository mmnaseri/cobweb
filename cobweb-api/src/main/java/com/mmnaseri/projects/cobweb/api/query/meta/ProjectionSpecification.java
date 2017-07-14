package com.mmnaseri.projects.cobweb.api.query.meta;

import com.mmnaseri.projects.cobweb.domain.content.Persistent;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:43 PM)
 */
public interface ProjectionSpecification<S, R> {

    R project(Persistent source);

}
