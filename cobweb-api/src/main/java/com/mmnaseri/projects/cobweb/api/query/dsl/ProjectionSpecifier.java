package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.query.meta.ProjectionSpecification;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:46 PM)
 */
public interface ProjectionSpecifier<K extends Serializable & Comparable<K>, P extends Persistent<K>> {

    <R> WhereClause<K, P, R> select(ProjectionSpecification<P, R> specification);

}
