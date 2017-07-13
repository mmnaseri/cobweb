package com.mmnaseri.projects.cobweb.api.graph;

import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:44 PM)
 */
public interface Graph<K extends Serializable & Comparable<K>> extends Serializable {

    String getName();

    IdentifierFactory<K> getIdentifierFactory();

    Vertex<K> createVertex();

    Edge<K> link(Vertex<K> from, Vertex<K> to);

    void unlink(Edge<K> edge);

    Tag<K> createTag(String name, String description);

    void renameTag(Tag<K> tag, String newName);

    void deleteVertex(Vertex<K> vertex);

    Attachment<K> createAttachment(String mime, String path);

    void attach(Document<K> document, Attachment<K> attachment);

    void tag(Document<K> document, Tag<K> tag);

    <P extends Persistent<K>> List<P> find(Query<K, P> query);

    <P extends Persistent<K>> long count(Query<K, P> query);

    <P extends Persistent<K>> boolean exists(Query<K, P> query);

    <P extends Persistent<K>> P findOne(Query<K, P> query);

}
