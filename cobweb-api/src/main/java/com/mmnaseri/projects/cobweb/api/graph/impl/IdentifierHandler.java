package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.graph.IdentifierGenerator;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:41 AM)
 */
public abstract class IdentifierHandler<K extends Serializable & Comparable<K>> {

    private IdentifierHandler() {
    }

    public abstract IdentifierGenerator<K> getIdentifierGenerator();

    public abstract IdentifierFactory<K> getIdentifierFactory();

    public static final IdentifierHandler<UUID> UUID_IDENTIFIER_HANDLER = new IdentifierHandler<UUID>() {
        @Override
        public IdentifierGenerator<UUID> getIdentifierGenerator() {
            return new UUIDIdentifierGenerator();
        }

        @Override
        public IdentifierFactory<UUID> getIdentifierFactory() {
            return IdentifierFactory.UUID_IDENTIFIER_FACTORY;
        }
    };

}
