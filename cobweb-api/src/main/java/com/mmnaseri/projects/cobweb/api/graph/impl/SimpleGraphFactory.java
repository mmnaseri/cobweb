package com.mmnaseri.projects.cobweb.api.graph.impl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:45 AM)
 */
public class SimpleGraphFactory {

    private final SimpleGraphConfigurationFactory configurationFactory;

    public SimpleGraphFactory() {
        configurationFactory = new SimpleGraphConfigurationFactory();
    }

    public SimpleGraph<UUID> getInstance(String name, Path root) throws IOException {
        return getInstance(name, root, ObjectInputOutputType.JACKSON);
    }

    public SimpleGraph<UUID> getInstance(String name, Path root, ObjectInputOutputType ioType) throws IOException {
        return getInstance(name, root, ioType, IdentifierHandler.UUID_IDENTIFIER_HANDLER);
    }

    public <K extends Serializable & Comparable<K>> SimpleGraph<K> getInstance(String name, Path root, ObjectInputOutputType ioType, IdentifierHandler<K> identifierHandler) throws IOException {
        return new SimpleGraph<>(configurationFactory.getConfiguration(name, root, ioType, identifierHandler));
    }


}
