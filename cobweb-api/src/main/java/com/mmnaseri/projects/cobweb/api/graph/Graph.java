package com.mmnaseri.projects.cobweb.api.graph;

import com.mmnaseri.projects.cobweb.api.query.Query;
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

    Vertex<K> createVertex(String label);

    Edge<K> link(Vertex<K> from, Vertex<K> to, String label);

    Tag<K> createTag(String name, String description);

    void renameTag(Tag<K> tag, String newName);

    void describeTag(Tag<K> tag, String newDescription);

    Attachment<K> createAttachment(String mime, Path path);

    void attach(Document<K> document, String name, Attachment<K> attachment);

    <D extends Document<K>> D update(D document);

    void tag(Document<K> document, Tag<K> tag);

    void delete(Persistent<K> persistent);

    <P extends Persistent<K>, R> List<R> find(Query<P, R> query);

    <P extends Persistent<K>, R> long count(Query<P, R> query);

    <P extends Persistent<K>, R> boolean exists(Query<P, R> query);

    <P extends Persistent<K>, R> R findOne(Query<P, R> query);

}
