package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl.DefaultValueConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 4:25 PM)
 */
public final class PersistentConditionals {

    private PersistentConditionals() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>, P extends Persistent<K>> ValueConditionalExpression<K, P, K> entityId(Sources<K, P> sources) {
        return new DefaultValueConditionalExpression<>("entity.id", object -> object.getId().getValue());
    }

}
