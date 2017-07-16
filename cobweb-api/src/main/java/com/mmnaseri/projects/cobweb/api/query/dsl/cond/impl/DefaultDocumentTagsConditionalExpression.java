package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.dsl.ConditionalExpression;
import com.mmnaseri.projects.cobweb.api.query.dsl.cond.DocumentTagsConditionalExpression;
import com.mmnaseri.projects.cobweb.domain.content.Document;
import com.mmnaseri.projects.cobweb.domain.content.Tag;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 6:40 PM)
 */
public class DefaultDocumentTagsConditionalExpression<K extends Serializable & Comparable<K>, P extends Document<K>> extends AbstractConditionalExpression<K, P> implements DocumentTagsConditionalExpression<K, P> {

    private final String subject;

    public DefaultDocumentTagsConditionalExpression(String subject) {
        this.subject = subject;
    }

    @Override
    public ConditionalExpression<K, P> contains(String tagName) {
        return setConditional(subject + " has " + tagName, value ->
                value.getTags().stream().anyMatch(
                        tag -> tag.getName().equals(tagName)
                )
        );
    }

    @Override
    public ConditionalExpression<K, P> contains(Tag<K> tag) {
        return setConditional(subject + " has " + tag.getName(), value -> value.getTags().stream().anyMatch(t -> tag.getId().getValue().equals(t.getId().getValue())));
    }

    @Override
    public ConditionalExpression<K, P> doesNotContain(String tagName) {
        return setConditional(subject + " !has " + tagName, value -> value.getTags().stream().noneMatch(tag -> tag.getName().equals(tagName)));
    }

    @Override
    public ConditionalExpression<K, P> doesNotContain(Tag<K> tag) {
        return setConditional(subject + " !has " + tag.getName(), value -> value.getTags().stream().noneMatch(t -> tag.getId().getValue().equals(t.getId().getValue())));
    }

}
