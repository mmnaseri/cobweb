package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.common.SerializableMap;
import com.mmnaseri.projects.cobweb.api.common.SerializableSet;
import com.mmnaseri.projects.cobweb.api.data.Index;
import com.mmnaseri.projects.cobweb.api.data.Stringifier;
import com.mmnaseri.projects.cobweb.api.data.impl.DirectoryIndexConfiguration;
import com.mmnaseri.projects.cobweb.api.data.impl.DirectoryIndexFactory;
import com.mmnaseri.projects.cobweb.api.io.ObjectInputOutputManager;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 3:28 PM)
 */
class SimpleGraphIndexFactory<K extends Serializable & Comparable<K>> {

    private final Path root;
    private final ObjectInputOutputManager inputOutputManager;
    private final Stringifier<K> stringifier;
    private final Index<K, VertexMetadata> vertices;
    private final Index<K, TagMetadata> tags;
    private final Index<K, AttachmentMetadata> attachments;
    private final Index<K, SerializableSet<K>> incomingEdges;
    private final Index<K, SerializableSet<K>> outgoingEdges;
    private final Index<K, SerializableMap<String, K>> documentAttachments;
    private final Index<K, SerializableSet<K>> anchors;
    private final Index<K, SerializableSet<K>> documentTags;
    private final Index<K, SerializableSet<K>> tagDocuments;
    private final Index<K, EdgeMetadata<K>> edges;

    SimpleGraphIndexFactory(Path root, ObjectInputOutputManager inputOutputManager, Stringifier<K> stringifier) {
        this.root = root;
        this.inputOutputManager = inputOutputManager;
        this.stringifier = stringifier;
        final DirectoryIndexFactory factory = new DirectoryIndexFactory();
        vertices = factory.getInstance(configureIndex("vertices"));
        edges = factory.getInstance(configureIndex("edges"));
        tags = factory.getInstance(configureIndex("tags"));
        attachments = factory.getInstance(configureIndex("attachments"));
        incomingEdges = factory.getInstance(configureIndex("incomingEdges"));
        outgoingEdges = factory.getInstance(configureIndex("outgoingEdges"));
        documentAttachments = factory.getInstance(configureIndex("documentAttachments"));
        anchors = factory.getInstance(configureIndex("anchors"));
        documentTags = factory.getInstance(configureIndex("documentTags"));
        tagDocuments = factory.getInstance(configureIndex("tagDocuments"));
    }

    private DirectoryIndexConfiguration configureIndex(String relativePath) {
        return DirectoryIndexConfiguration
                    .forKeyType(new ParameterizedTypeReference<K>() {
                    })
                    .andRootPath(root.resolve(relativePath))
                    .andIOManager(inputOutputManager)
                    .andStringifier(stringifier)
                    .build();
    }

    Index<K, VertexMetadata> vertices() {
        return vertices;
    }

    Index<K, TagMetadata> tags() {
        return tags;
    }

    Index<K, AttachmentMetadata> attachments() {
        return attachments;
    }

    Index<K, SerializableSet<K>> incomingEdges() {
        return incomingEdges;
    }

    Index<K, SerializableSet<K>> outgoingEdges() {
        return outgoingEdges;
    }

    Index<K, SerializableMap<String, K>> documentAttachments() {
        return documentAttachments;
    }

    Index<K, SerializableSet<K>> anchors() {
        return anchors;
    }

    Index<K, SerializableSet<K>> documentTags() {
        return documentTags;
    }

    Index<K, SerializableSet<K>> tagDocuments() {
        return tagDocuments;
    }

    Index<K, EdgeMetadata<K>> edges() {
        return edges;
    }

}
