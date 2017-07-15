package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.query.Query;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:50 PM)
 */
public interface WhereClause<K extends Serializable & Comparable<K>, P extends Persistent<K>, R> {

    Query<P, R> where(ConditionalExpression<K, P> expression);

    Query<P, R> whereNot(ConditionalExpression<K, P> expression);

    Query<P, R> all();

    Query<P, R> id(K key);

    Query<P, R> id(Identifier<K> identifier);

}
