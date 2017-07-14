package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.data.ix.DocumentIndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.InvertedIndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.NamedInvertedIndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.PersistentIndexWrapper;
import com.mmnaseri.projects.cobweb.api.graph.Graph;
import com.mmnaseri.projects.cobweb.api.graph.impl.domain.*;
import com.mmnaseri.projects.cobweb.api.query.Query;
import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:31 PM)
 */
public class SimpleGraph<K extends Serializable & Comparable<K>> implements Graph<K, SimpleGraphConfiguration<K>> {

    private static final long serialVersionUID = -1272124666112477285L;
    private final SimpleGraphConfiguration<K> configuration;
    private final DocumentIndexWrapper<K, Vertex<K>> verticesIndex;
    private final DocumentIndexWrapper<K, Edge<K>> edgesIndex;
    private final PersistentIndexWrapper<K, Attachment<K>> attachmentsIndex;
    private final PersistentIndexWrapper<K, Tag<K>> tagsIndex;
    private final InvertedIndexWrapper<K> vertexIncomingIndex;
    private final InvertedIndexWrapper<K> vertexOutgoingIndex;
    private final InvertedIndexWrapper<K> attachmentAnchorsIndex;
    private final InvertedIndexWrapper<K> documentTagIndex;
    private final InvertedIndexWrapper<K> tagDocumentIndex;
    private final NamedInvertedIndexWrapper<K> documentAttachmentIndex;

    public SimpleGraph(SimpleGraphConfiguration<K> configuration) throws IOException {
        this.configuration = configuration;
        final SimpleGraphIndexFactory<K> indexes = configuration.getIndexes();
        verticesIndex = new DocumentIndexWrapper<>(indexes.vertices());
        vertexIncomingIndex = new InvertedIndexWrapper<>(indexes.incomingEdges());
        vertexOutgoingIndex = new InvertedIndexWrapper<>(indexes.outgoingEdges());
        edgesIndex = new DocumentIndexWrapper<>(indexes.edges());
        tagsIndex = new PersistentIndexWrapper<>(indexes.tags());
        attachmentsIndex = new PersistentIndexWrapper<>(indexes.attachments());
        documentAttachmentIndex = new NamedInvertedIndexWrapper<>(indexes.documentAttachments());
        attachmentAnchorsIndex = new InvertedIndexWrapper<>(indexes.anchors());
        documentTagIndex = new InvertedIndexWrapper<>(indexes.documentTags());
        tagDocumentIndex = new InvertedIndexWrapper<>(indexes.tagDocuments());
    }

    @Override
    public SimpleGraphConfiguration<K> getConfiguration() {
        return configuration;
    }

    @Override
    public Vertex<K> createVertex() {
        final Vertex<K> vertex = new Vertex<>();
        vertex.setId(nextId());
        vertex.setIncoming(new LinkedList<>());
        vertex.setOutgoing(new LinkedList<>());
        vertex.setAttachments(new HashMap<>());
        vertex.setProperties(new DocumentProperties());
        vertex.setTags(new HashSet<>());
        return wrap(verticesIndex.save(vertex));
    }

    @Override
    public Edge<K> link(Vertex<K> from, Vertex<K> to) {
        final Edge<K> edge = new Edge<>();
        edge.setId(nextId());
        edge.setFrom(from);
        edge.setTo(to);
        edge.setAttachments(new HashMap<>());
        edge.setProperties(new DocumentProperties());
        edge.setTags(new HashSet<>());
        vertexOutgoingIndex.add(from, edge);
        vertexIncomingIndex.add(to, edge);
        return wrap(edgesIndex.save(edge));
    }

    @Override
    public Tag<K> createTag(String name, String description) {
        if (exists(null)) {
            throw new IllegalStateException();
        }
        final Tag<K> tag = new Tag<>();
        tag.setId(nextId());
        tag.setName(name);
        tag.setDescription(description);
        return wrap(tagsIndex.save(tag));
    }

    @Override
    public void renameTag(Tag<K> tag, String newName) {
        if (exists(null)) {
            throw new IllegalStateException();
        }
        final Tag<K> found = findOne(null);
        found.setName(newName);
        found.setDocuments(Collections.emptyList());
        tagsIndex.save(found);
    }

    @Override
    public void describeTag(Tag<K> tag, String newDescription) {
        final Tag<K> found = findOne(null);
        found.setDescription(newDescription);
        found.setDocuments(Collections.emptyList());
        tagsIndex.save(found);
    }

    @Override
    public Attachment<K> createAttachment(String mime, Path path) {
        final Attachment<K> attachment = new Attachment<>();
        attachment.setId(nextId());
        attachment.setAnchors(new HashSet<>());
        attachment.setMime(mime);
        copyAttachment(attachment, path);
        return wrap(attachmentsIndex.save(attachment));
    }

    @Override
    public void attach(Document<K> document, String name, Attachment<K> attachment) {
        documentAttachmentIndex.put(document, name, attachment);
        attachmentAnchorsIndex.add(attachment, document);
    }

    @Override
    public <D extends Document<K>> D update(D document) {
        final DocumentProperties properties = document.getProperties();
        if (document instanceof Vertex<?>) {
            verticesIndex.save(document.getId(), properties);
        } else if (document instanceof Edge<?>) {
            edgesIndex.save(document.getId(), properties);
        } else {
            throw new IllegalArgumentException();
        }
        return wrap(document);
    }

    @Override
    public void tag(Document<K> document, Tag<K> tag) {
        documentTagIndex.add(document, tag);
        tagDocumentIndex.add(tag, document);
    }

    @Override
    public void delete(Persistent<K> persistent) {
        if (persistent instanceof Attachment<?>) {
            deleteAttachment(((Attachment<K>) persistent));
        } else if (persistent instanceof Tag<?>) {
            deleteTag(((Tag<K>) persistent));
        } else if (persistent instanceof Edge<?>) {
            deleteEdge(((Edge<K>) persistent));
        } else if (persistent instanceof Vertex<?>) {
            deleteVertex(((Vertex<K>) persistent));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void deleteAttachment(Attachment<K> attachment) {
        attachmentAnchorsIndex.removeFromInvertedIndex(attachment, documentAttachmentIndex);
        attachmentAnchorsIndex.delete(attachment);
        attachmentsIndex.delete(attachment.getId());
        try {
            Files.deleteIfExists(getAttachmentFilePath(attachment));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deleteTag(Tag<K> tag) {
        tagDocumentIndex.removeFromInvertedIndex(tag, documentTagIndex);
        tagDocumentIndex.delete(tag);
        tagsIndex.delete(tag.getId());
    }

    private void deleteEdge(Edge<K> edge) {
        final Edge<K> found = findOne(null);
        vertexOutgoingIndex.remove(found.getFrom(), found);
        vertexIncomingIndex.remove(found.getTo(), found);
        deleteDocument(found);
        edgesIndex.delete(found);
    }

    private void deleteVertex(Vertex<K> vertex) {
        final Vertex<K> found = findOne(null);
        deleteDocument(found);
        verticesIndex.delete(found);
    }

    private void deleteDocument(Document<K> found) {
        documentTagIndex.removeFromInvertedIndex(found, tagDocumentIndex);
        documentTagIndex.delete(found);
        documentAttachmentIndex.removeFromInvertedIndex(found, attachmentAnchorsIndex);
        documentAttachmentIndex.delete(found);
    }

    @Override
    public <P extends Persistent<K>> List<P> find(Query<K, P> query) {
        //todo
        return null;
    }

    @Override
    public <P extends Persistent<K>> long count(Query<K, P> query) {
        //todo
        return 0;
    }

    @Override
    public <P extends Persistent<K>> boolean exists(Query<K, P> query) {
        //todo
        return false;
    }

    @Override
    public <P extends Persistent<K>> P findOne(Query<K, P> query) {
        //todo
        return null;
    }

    private Identifier<K> nextId() {
        return getConfiguration().getIdentifierFactory().getIdentifier(getConfiguration().getIdentifierGenerator().next());
    }

    private void copyAttachment(Attachment<K> attachment, Path path) {
        try {
            if (!Files.exists(getConfiguration().getAttachmentsPath())) {
                Files.createDirectories(getConfiguration().getAttachmentsPath());
            } else if (!Files.isDirectory(getConfiguration().getAttachmentsPath())) {
                throw new IllegalStateException();
            }
            Files.copy(path, getAttachmentFilePath(attachment));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getAttachmentFilePath(Attachment<K> attachment) {
        return getConfiguration().getAttachmentsPath().resolve(getConfiguration().getIdentifierFactory().toString(attachment.getId()));
    }

    @SuppressWarnings("unchecked")
    private <P extends Persistent<K>> P wrap(P persistent){
        if (persistent instanceof LazyPersistent) {
            return persistent;
        }
        if (persistent instanceof Tag<?>) {
            return (P) new LazyTag<K>((Tag) persistent, null);
        } else if (persistent instanceof Attachment<?>) {
            return (P) new LazyAttachment<K>((Attachment) persistent, null);
        } else if (persistent instanceof Document<?>) {
            final LazyLoader<Set<Tag<K>>> tagLoader = null;
            final LazyLoader<Map<String, Attachment<K>>> attachmentLoader = null;
            if (persistent instanceof Edge<?>) {
                return (P) new LazyEdge<K>((Edge) persistent, tagLoader, attachmentLoader, null, null);
            } else if (persistent instanceof Vertex<?>) {
                return (P) new LazyVertex<K>((Vertex) persistent, tagLoader, attachmentLoader, null, null);
            }
        }
        throw new IllegalArgumentException();
    }

}
