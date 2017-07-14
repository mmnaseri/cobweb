package com.mmnaseri.projects.cobweb.api.data.impl;

import com.mmnaseri.projects.cobweb.api.data.Stringifier;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/14/17, 1:22 PM)
 */
public class IdentifierFactoryStringifier<K extends Serializable & Comparable<K>, P extends Persistent<K>> implements Stringifier<K> {

    private static final long serialVersionUID = -6932861839747164596L;
    private final IdentifierFactory<K> factory;

    public IdentifierFactoryStringifier(IdentifierFactory<K> factory) {
        this.factory = factory;
    }

    @Override
    public K fromString(String string) {
        return factory.fromString(string).getValue();
    }

    @Override
    public String toString(K value) {
        return factory.toString(factory.getIdentifier(value));
    }

}
