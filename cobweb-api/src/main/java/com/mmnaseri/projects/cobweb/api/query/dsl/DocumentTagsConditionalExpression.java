package com.mmnaseri.projects.cobweb.api.query.dsl;

import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.content.Tag;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 6:39 PM)
 */
public interface DocumentTagsConditionalExpression<K extends Serializable & Comparable<K>, P extends Document<K>> extends ConditionalExpression<K, P> {

    ConditionalExpression<K, P> contains(String tagName);

    ConditionalExpression<K, P> contains(Tag<K> tag);

    ConditionalExpression<K, P> doesNotContain(String tagName);

    ConditionalExpression<K, P> doesNotContain(Tag<K> tag);

}
