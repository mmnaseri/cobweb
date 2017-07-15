package com.mmnaseri.projects.cobweb.api.graph.impl;

import com.mmnaseri.projects.cobweb.api.common.ParameterizedTypeReference;
import com.mmnaseri.projects.cobweb.api.data.ix.DocumentIndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.IndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.InvertedIndexWrapper;
import com.mmnaseri.projects.cobweb.api.data.ix.NamedInvertedIndexWrapper;
import com.mmnaseri.projects.cobweb.api.graph.Graph;
import com.mmnaseri.projects.cobweb.api.graph.impl.domain.*;
import com.mmnaseri.projects.cobweb.api.query.Query;
import com.mmnaseri.projects.cobweb.api.query.dsl.Sources;
import com.mmnaseri.projects.cobweb.api.query.meta.Conditional;
import com.mmnaseri.projects.cobweb.api.query.meta.ProjectionSpecification;
import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.mmnaseri.projects.cobweb.api.query.dsl.Projections.itself;
import static com.mmnaseri.projects.cobweb.api.query.dsl.QueryBuilder.from;
import static com.mmnaseri.projects.cobweb.api.query.dsl.cond.PersistentConditionals.entityId;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:31 PM)
 */
public class SimpleGraph<K extends Serializable & Comparable<K>> implements Graph<K, SimpleGraphConfiguration<K>> {

    private static final long serialVersionUID = -1272124666112477285L;
    private final SimpleGraphConfiguration<K> configuration;
    private final DocumentIndexWrapper<K, Vertex<K>> verticesIndex;
    private final IndexWrapper<K, AttachmentMetadata> attachmentsIndex;
    private final IndexWrapper<K, TagMetadata> tagsIndex;
    private final IndexWrapper<K, EdgeMetadata<K>> edgesIndex;
    private final InvertedIndexWrapper<K> vertexIncomingIndex;
    private final InvertedIndexWrapper<K> vertexOutgoingIndex;
    private final InvertedIndexWrapper<K> attachmentAnchorsIndex;
    private final InvertedIndexWrapper<K> documentTagIndex;
    private final InvertedIndexWrapper<K> tagDocumentIndex;
    private final NamedInvertedIndexWrapper<K> documentAttachmentIndex;
    private final GraphSources sources;

    public SimpleGraph(SimpleGraphConfiguration<K> configuration) throws IOException {
        this.configuration = configuration;
        final SimpleGraphIndexFactory<K> indexes = configuration.getIndexes();
        verticesIndex = new DocumentIndexWrapper<>(indexes.vertices());
        vertexIncomingIndex = new InvertedIndexWrapper<>(indexes.incomingEdges());
        vertexOutgoingIndex = new InvertedIndexWrapper<>(indexes.outgoingEdges());
        edgesIndex = new IndexWrapper<>(indexes.edges());
        tagsIndex = new IndexWrapper<>(indexes.tags());
        attachmentsIndex = new IndexWrapper<>(indexes.attachments());
        documentAttachmentIndex = new NamedInvertedIndexWrapper<>(indexes.documentAttachments());
        attachmentAnchorsIndex = new InvertedIndexWrapper<>(indexes.anchors());
        documentTagIndex = new InvertedIndexWrapper<>(indexes.documentTags());
        tagDocumentIndex = new InvertedIndexWrapper<>(indexes.tagDocuments());
        sources = new GraphSources();
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
        edgesIndex.save(edge.getId(), new EdgeMetadata<>(edge));
        return wrap(edge);
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
        tagsIndex.save(tag.getId(), new TagMetadata(tag));
        return wrap(tag);
    }

    @Override
    public void renameTag(Tag<K> tag, String newName) {
        if (exists(null)) {
            throw new IllegalStateException();
        }
        final Tag<K> found = findById(tag);
        found.setName(newName);
        found.setDocuments(Collections.emptyList());
        tagsIndex.save(tag.getId(), new TagMetadata(tag));
    }

    @Override
    public void describeTag(Tag<K> tag, String newDescription) {
        final Tag<K> found = findById(tag);
        found.setDescription(newDescription);
        found.setDocuments(Collections.emptyList());
        tagsIndex.save(tag.getId(), new TagMetadata(tag));
    }

    @Override
    public Attachment<K> createAttachment(String mime, Path path) {
        final Attachment<K> attachment = new Attachment<>();
        attachment.setId(nextId());
        attachment.setAnchors(new HashSet<>());
        attachment.setMime(mime);
        copyAttachment(attachment, path);
        attachmentsIndex.save(attachment.getId(), new AttachmentMetadata(attachment));
        return wrap(attachment);
    }

    @Override
    public void attach(Document<K> document, String name, Attachment<K> attachment) {
        documentAttachmentIndex.put(document, name, attachment);
        attachmentAnchorsIndex.add(attachment, document);
    }

    @Override
    public <D extends Document<K>> D update(D document) {
        if (document instanceof Vertex<?>) {
            //noinspection unchecked
            verticesIndex.save((Vertex<K>) document);
        } else if (document instanceof Edge<?>) {
            //noinspection unchecked
            final EdgeMetadata<K> metadata = edgesIndex.read(document.getId(), k -> new EdgeMetadata<>((Edge<K>) document));
            if (((Edge) document).getFrom() != null && ((Edge) document).getTo() != null) {
                //noinspection unchecked
                if (!metadata.getFrom().equals(((Edge) document).getFrom().getId().getValue())) {
                    //noinspection unchecked
                    metadata.setFrom((K) ((Edge) document).getFrom().getId().getValue());
                }
                if (!metadata.getTo().equals(((Edge) document).getTo().getId().getValue())) {
                    //noinspection unchecked
                    metadata.setTo((K) ((Edge) document).getTo().getId().getValue());
                }
            }
            metadata.setProperties(document.getProperties());
            edgesIndex.save(document.getId(), metadata);
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
        final Edge<K> found = findById(edge);
        vertexOutgoingIndex.remove(found.getFrom(), found);
        vertexIncomingIndex.remove(found.getTo(), found);
        deleteDocument(found);
        edgesIndex.delete(found.getId());
    }

    private void deleteVertex(Vertex<K> vertex) {
        final Vertex<K> found = findById(vertex);
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
    public <P extends Persistent<K>, R> List<R> find(Query<P, R> query) {
        final List<?> list = query(query, 0, -1, true);
        //noinspection unchecked
        return (List<R>) list;
    }

    @Override
    public <P extends Persistent<K>, R> long count(Query<P, R> query) {
        return query(query, 0, -1, false).size();
    }

    @Override
    public <P extends Persistent<K>, R> boolean exists(Query<P, R> query) {
        return !query(query, 0, 1, false).isEmpty();
    }

    @Override
    public <P extends Persistent<K>, R> R findOne(Query<P, R> query) {
        final List<?> list = query(query, 1, 1, true);
        //noinspection unchecked
        return (R) list.get(0);
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
    private <P extends Persistent<K>> P wrap(P persistent) {
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
                return (P) new LazyEdge<K>((Edge) persistent, tagLoader, attachmentLoader);
            } else if (persistent instanceof Vertex<?>) {
                return (P) new LazyVertex<K>((Vertex) persistent, tagLoader, attachmentLoader, null, null);
            }
        }
        throw new IllegalArgumentException();
    }

    @SuppressWarnings("unchecked")
    private <P extends Persistent<K>> P findById(P sample) {
        final Sources<K, P> sources;
        if (sample instanceof Edge<?>) {
            sources = (Sources<K, P>) this.sources.edges;
        } else if (sample instanceof Vertex<?>) {
            sources = (Sources<K, P>) this.sources.vertices;
        } else if (sample instanceof Attachment<?>) {
            sources = (Sources<K, P>) this.sources.attachments;
        } else if (sample instanceof Tag<?>) {
            sources = (Sources<K, P>) this.sources.tags;
        } else {
            throw new IllegalStateException();
        }
        return findOne(from(sources).select(itself()).where(entityId(sources).is(sample.getId().getValue())));
    }

    private class GraphSources {

        private final Sources<K, Edge<K>> edges = Sources.edges(new ParameterizedTypeReference<K>() {
        });

        private final Sources<K, Vertex<K>> vertices = Sources.vertices(new ParameterizedTypeReference<K>() {
        });

        private final Sources<K, Attachment<K>> attachments = Sources.attachments(new ParameterizedTypeReference<K>() {
        });

        private final Sources<K, Tag<K>> tags = Sources.tags(new ParameterizedTypeReference<K>() {
        });

    }

    @SuppressWarnings("unchecked")
    private <P extends Persistent<K>, R> List<?> query(Query<P, R> query, int min, int max, boolean project) {
        final Class<? extends Persistent> persistentType = query.getSources().getPersistentType();
        final IndexWrapper<K, ? extends Serializable> index;
        final Reader<K, Serializable, P> reader;
        if (Tag.class.equals(persistentType)) {
            index = tagsIndex;
            reader = (key, value) -> (P) ((TagMetadata) value).toTag(getConfiguration().getIdentifierFactory().getIdentifier(key));
        } else if (Attachment.class.equals(persistentType)) {
            index = attachmentsIndex;
            reader = (key, value) -> (P) ((AttachmentMetadata) value).toAttachment(getConfiguration().getIdentifierFactory().getIdentifier(key));
        } else if (Vertex.class.equals(persistentType)) {
            index = verticesIndex;
            reader = (key, value) -> {
                final Vertex<K> vertex = new Vertex<>();
                vertex.setId(getConfiguration().getIdentifierFactory().getIdentifier(key));
                vertex.setTags(Collections.emptySet());
                vertex.setAttachments(Collections.emptyMap());
                vertex.setIncoming(Collections.emptyList());
                vertex.setOutgoing(Collections.emptyList());
                vertex.setProperties(((DocumentProperties) value));
                return (P) vertex;
            };
        } else if (Edge.class.equals(persistentType)) {
            index = edgesIndex;
            reader = (key, value) -> {
                final Edge<K> edge = ((EdgeMetadata<K>) value).toEdge(key, configuration.getIdentifierFactory());
                edge.setFrom(wrap(edge.getFrom()));
                edge.setTo(wrap(edge.getTo()));
                return (P) edge;
            };
        } else {
            throw new IllegalStateException();
        }
        final List<K> keys = index.getIndex().keys();
        final List<Object> result = new LinkedList<>();
        final Conditional<P> conditional = query.getConditional();
        final ProjectionSpecification<P, R> projectionSpecification = query.getProjectionSpecification();
        int found = 0;
        for (final K key : keys) {
            final Serializable value = index.read(key, null);
            final P converted = wrap(reader.read(key, value));
            if (conditional.test(converted)) {
                found++;
                if (project) {
                    result.add(projectionSpecification.project(converted));
                } else {
                    result.add(converted);
                }
                if (max > 0 && found >= max) {
                    break;
                }
            }
        }
        if (found < min) {
            throw new IllegalStateException();
        }
        return result;
    }

    private interface Reader<K extends Serializable & Comparable<K>, E, P extends Persistent<K>> {

        P read(K key, E value);

    }

}
