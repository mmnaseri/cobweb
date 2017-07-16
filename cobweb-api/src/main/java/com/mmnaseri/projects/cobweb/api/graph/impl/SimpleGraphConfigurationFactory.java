package com.mmnaseri.projects.cobweb.api.graph.impl;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:41 AM)
 */
public class SimpleGraphConfigurationFactory {

    public SimpleGraphConfiguration<UUID> getConfiguration(String name, Path root) {
        return getConfiguration(name, root, ObjectInputOutputType.JACKSON);
    }

    public SimpleGraphConfiguration<UUID> getConfiguration(String name, Path root, ObjectInputOutputType ioType) {
        return getConfiguration(name, root, ioType, IdentifierHandler.UUID_IDENTIFIER_HANDLER);
    }

    public <K extends Serializable & Comparable<K>> SimpleGraphConfiguration<K> getConfiguration(String name, Path root, ObjectInputOutputType ioType, IdentifierHandler<K> identifierHandler) {
        return new SimpleGraphConfiguration<>(name, identifierHandler.getIdentifierFactory(), identifierHandler.getIdentifierGenerator(), root, ioType.factory().getInstance());
    }

}
