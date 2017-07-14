package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.api.query.Query;
import com.mmnaseri.projects.cobweb.api.query.dsl.impl.SimpleConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.api.query.meta.ProjectionSpecification;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:43 PM)
 */
public class QueryBuilder<K extends Serializable & Comparable<K>, P extends Persistent<K>, R> implements WhereClause<K, P, R>, ProjectionSpecifier<K, P> {

    public static <K extends Serializable & Comparable<K>, P extends Persistent<K>> ProjectionSpecifier<K, P> from(Sources<K, P> sources) {
        return new QueryBuilder<>(source -> source, null);
    }

    private final ProjectionSpecification<P, R> projectionSpecification;
    private final ConditionalExpression<K, P> expression;

    private QueryBuilder(ProjectionSpecification<P, R> projectionSpecification, ConditionalExpression<K, P> expression) {
        this.projectionSpecification = projectionSpecification;
        this.expression = expression;
    }

    @Override
    public Query<P, R> where(ConditionalExpression<K, P> expression) {
        final Conditional<P> conditional = expression.getConditional();
        return new ImmutableQuery<>(conditional, projectionSpecification);
    }

    @Override
    public Query<P, R> whereNot(ConditionalExpression<K, P> expression) {
        return where(new SimpleConditionalExpression<>(expression.getConditional().not()));
    }

    @Override
    public <S> WhereClause<K, P, S> select(ProjectionSpecification<P, S> specification) {
        return new QueryBuilder<>(specification, expression);
    }

    private static final class ImmutableQuery<S, R> implements Query<S, R> {

        private final Conditional<S> conditional;
        private final ProjectionSpecification<S, R> projectionSpecification;

        private ImmutableQuery(Conditional<S> conditional, ProjectionSpecification<S, R> projectionSpecification) {
            this.conditional = conditional;
            this.projectionSpecification = projectionSpecification;
        }

        @Override
        public Conditional<S> getConditional() {
            return conditional;
        }

        @Override
        public ProjectionSpecification<S, R> getProjectionSpecification() {
            return projectionSpecification;
        }

    }

}
