package com.mmnaseri.projects.cobweb.api.query.dsl.cond.impl;

import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/15/17, 12:16 PM)
 */
public class IdentifierConditional<K extends Serializable & Comparable<K>, P extends Persistent<K>> implements Conditional<P> {

    private final K key;

    public IdentifierConditional(K key) {
        this.key = key;
    }

    @Override
    public boolean test(P value) {
        return value.getId().getValue().equals(key);
    }

    public K getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "id == " + key;
    }

}
