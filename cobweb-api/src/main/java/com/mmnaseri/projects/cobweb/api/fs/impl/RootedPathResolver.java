package com.mmnaseri.projects.cobweb.api.fs.impl;

import com.mmnaseri.projects.cobweb.api.db.Graph;
import com.mmnaseri.projects.cobweb.api.fs.PathResolver;
import com.mmnaseri.projects.cobweb.domain.content.ObjectType;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Objects;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:39 PM)
 */
public class RootedPathResolver implements PathResolver {

    private final Path root;
    private static final EnumMap<ObjectType, String> KEYS = new EnumMap<>(ObjectType.class);

    static {
        KEYS.put(ObjectType.ATTACHMENT, "attachments");
        KEYS.put(ObjectType.DOCUMENT, "documents");
        KEYS.put(ObjectType.TAG, "tags");
    }

    public RootedPathResolver(Path root) {
        this.root = Objects.requireNonNull(root, "Root cannot be null");
    }

    @Override
    public <I extends Serializable> Path resolve(Graph<I> graph, Persistent<I> persistent) {
        return resolve(graph, persistent.getObjectType(), persistent.getId());
    }

    @Override
    public <I extends Serializable> Path resolve(Graph<I> graph, ObjectType type, Identifier<I> identifier) {
        return resolve(graph, type).resolve(String.valueOf(identifier.getValue()));
    }

    @Override
    public <I extends Serializable> Path resolve(Graph<I> graph, ObjectType type) {
        return root.resolve(graph.getName()).resolve(KEYS.get(type));
    }

}
