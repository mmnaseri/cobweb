package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.graph.GraphConfiguration;
import com.mmnaseri.projects.cobweb.api.graph.IdentifierGenerator;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 3:24 PM)
 */
public class SimpleGraphConfiguration<K extends Serializable & Comparable<K>> implements GraphConfiguration<K> {

    private static final long serialVersionUID = 6503500292735851352L;
    private final String name;
    private final IdentifierFactory<K> identifierFactory;
    private final IdentifierGenerator<K> identifierGenerator;
    private final Path root;
    private final SimpleGraphIndexFactory<K> indexFactory;

    public SimpleGraphConfiguration(String name, IdentifierFactory<K> identifierFactory, IdentifierGenerator<K> identifierGenerator, Path root) {
        this.name = name;
        this.identifierFactory = identifierFactory;
        this.identifierGenerator = identifierGenerator;
        this.root = root;
        indexFactory = new SimpleGraphIndexFactory<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IdentifierFactory<K> getIdentifierFactory() {
        return identifierFactory;
    }

    @Override
    public IdentifierGenerator<K> getIdentifierGenerator() {
        return identifierGenerator;
    }

    public Path getRoot() {
        return root;
    }

    public SimpleGraphIndexFactory<K> getIndexes() {
        return indexFactory;
    }

    public Path getAttachmentsPath() {
        return root.resolve("attachments");
    }

}