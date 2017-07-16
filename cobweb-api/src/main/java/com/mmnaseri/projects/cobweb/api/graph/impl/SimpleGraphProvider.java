package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.graph.Graph;
import com.mmnaseri.projects.cobweb.api.graph.GraphConfiguration;
import com.mmnaseri.projects.cobweb.api.graph.GraphProvider;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;
import com.mmnaseri.projects.cobweb.api.io.impl.SerializableObjectInputOutputManager;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:05 AM)
 */
public class SimpleGraphProvider implements GraphProvider {

    private final Path root;
    private final ObjectInputOutputManager ioManager;

    public SimpleGraphProvider(Path root) {
        if (!Files.isDirectory(root)) {
            throw new IllegalStateException();
        }
        this.root = root;
        ioManager = new SerializableObjectInputOutputManager();
    }

    @Override
    public <K extends Serializable & Comparable<K>> Graph<K, ?> getGraph(String name, Class<K> keyType) throws IOException {
        final Path graphDir = root.resolve(name);
        if (!Files.isDirectory(graphDir)) {
            throw new IllegalStateException();
        }
        final Path descriptorFile = graphDir.resolve("graph.db");
        if (!Files.exists(descriptorFile)) {
            throw new IllegalStateException();
        }
        return ioManager.getObjectReader().read(descriptorFile, new ParameterizedTypeReference<Graph<K, ?>>() {
        });
    }

    @Override
    public <K extends Serializable & Comparable<K>, C extends GraphConfiguration<K>> Graph<K, C> save(Graph<K, C> graph) throws IOException {
        final C configuration = graph.getConfiguration();
        final Path graphDir = root.resolve(configuration.getName());
        if (!Files.exists(graphDir)) {
            Files.createDirectories(graphDir);
        }
        ioManager.getObjectWriter().write(graphDir.resolve("graph.db"), graph);
        return graph;
    }

}
