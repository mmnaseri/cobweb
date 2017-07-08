package com.mmnaseri.projects.cobweb.api.db;

import com.mmnaseri.projects.cobweb.api.fs.PathResolver;
import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:32 PM)
 */
public interface Graph<I extends Serializable> {

    String getName();

    PathResolver getPathResolver();

    Set<Tag<I>> getTags();

    Tag<I> createTag(String name, String description);

    Vertex<I> createVertex();

    Attachment<I> createAttachment();

    Edge<I> createEdge(Vertex<I> from, Vertex<I> to);

    <P extends Persistent<I>> P save(P persistent);

    default Persistent<? extends I> findById(Identifier<I> identifier) {
        //noinspection unchecked
        return findById(identifier, null);
    }

    <P extends Persistent<? extends I>> P findById(Identifier<I> identifier, Class<P> type);

    List<Object> query(String query);

}
