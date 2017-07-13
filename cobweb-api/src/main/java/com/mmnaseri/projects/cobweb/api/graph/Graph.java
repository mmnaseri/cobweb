package com.mmnaseri.projects.cobweb.api.graph;

import com.mmnaseri.projects.cobweb.domain.content.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:44 PM)
 */
public interface Graph<K extends Serializable & Comparable<K>, C extends GraphConfiguration<K>> extends Serializable {

    C getConfiguration();

    Vertex<K> createVertex();

    Edge<K> link(Vertex<K> from, Vertex<K> to);

    Tag<K> createTag(String name, String description);

    void renameTag(Tag<K> tag, String newName);

    void describeTag(Tag<K> tag, String newDescription);

    Attachment<K> createAttachment(String mime, Path path);

    void attach(Document<K> document, String name, Attachment<K> attachment);

    <D extends Document<K>> D update(D document);

    void tag(Document<K> document, Tag<K> tag);

    void delete(Persistent<K> persistent);

    <P extends Persistent<K>> List<P> find(Query<K, P> query);

    <P extends Persistent<K>> long count(Query<K, P> query);

    <P extends Persistent<K>> boolean exists(Query<K, P> query);

    <P extends Persistent<K>> P findOne(Query<K, P> query);

}
