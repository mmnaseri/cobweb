package com.mmnaseri.projects.cobweb.api.query.dsl.cond;

import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl.DefaultStringConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Tag;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 2:56 PM)
 */
public final class TagConditionals {

    private TagConditionals() {
        throw new UnsupportedOperationException();
    }

    public static <K extends Serializable & Comparable<K>> StringConditionalExpression<K, Tag<K>> tagName(Sources<K, Tag<K>> sources) {
        return new DefaultStringConditionalExpression<>("name", Tag::getName);
    }

    public static <K extends Serializable & Comparable<K>> StringConditionalExpression<K, Tag<K>> tagDescription(Sources<K, Tag<K>> sources) {
        return new DefaultStringConditionalExpression<>("description", Tag::getDescription);
    }

}
