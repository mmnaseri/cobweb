package com.mmnaseri.projects.cobweb.domain.id;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:15 PM)
 */
public abstract class IdentifierFactory<I extends Serializable & Comparable<I>> {

    private IdentifierFactory() {}

    public abstract Identifier<I> getIdentifier(I value);

    public abstract Identifier<I> fromString(String value);

    public abstract Identifier<I> produce();

    public abstract String toString(Identifier<I> identifier);

    public static final IdentifierFactory<UUID> UUID_IDENTIFIER_FACTORY = new IdentifierFactory<UUID>() {

        public Identifier<UUID> getIdentifier(UUID value) {
            return new Identifier<UUID>(value);
        }

        @Override
        public Identifier<UUID> fromString(String value) {
            return getIdentifier(UUID.fromString(value));
        }

        @Override
        public Identifier<UUID> produce() {
            return getIdentifier(UUID.randomUUID());
        }

        @Override
        public String toString(Identifier<UUID> identifier) {
            return identifier.getValue().toString();
        }

    };
}
