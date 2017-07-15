package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.data.impl.IdentifierFactoryStringifier;
import com.mmnaseri.projects.cobweb.api.graph.GraphConfiguration;
import com.mmnaseri.projects.cobweb.api.graph.IdentifierGenerator;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 3:24 PM)
 */
public class SimpleGraphConfiguration<K extends Serializable & Comparable<K>> implements GraphConfiguration<K> {

    private static final long serialVersionUID = 6503500292735851352L;
    private String name;
    private IdentifierFactory<K> identifierFactory;
    private IdentifierGenerator<K> identifierGenerator;
    private Path root;
    private ObjectInputOutputManager inputOutputManager;
    private SimpleGraphIndexFactory<K> indexFactory;

    public SimpleGraphConfiguration(String name, IdentifierFactory<K> identifierFactory, IdentifierGenerator<K> identifierGenerator, Path root, ObjectInputOutputManager inputOutputManager) {
        if (!(identifierGenerator instanceof Serializable)) {
            throw new IllegalStateException();
        }
        this.name = name;
        this.identifierFactory = identifierFactory;
        this.identifierGenerator = identifierGenerator;
        this.root = root;
        this.inputOutputManager = inputOutputManager;
        indexFactory = new SimpleGraphIndexFactory<>(root, inputOutputManager, new IdentifierFactoryStringifier<>(identifierFactory));
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
        return root.resolve("attachmentFiles");
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(name);
        out.writeObject(identifierFactory);
        out.writeObject(identifierGenerator);
        out.writeObject(inputOutputManager);
        out.writeObject(root.toUri());
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = ((String) in.readObject());
        identifierFactory = (IdentifierFactory<K>) in.readObject();
        identifierGenerator = (IdentifierGenerator<K>) in.readObject();
        inputOutputManager = (ObjectInputOutputManager) in.readObject();
        root = FileSystems.getDefault().provider().getPath((URI) in.readObject());
        indexFactory = new SimpleGraphIndexFactory<>(root, inputOutputManager, new IdentifierFactoryStringifier<>(identifierFactory));
    }

}