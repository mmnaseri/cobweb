package com.mmnaseri.projects.cobweb.api;

import com.mmnaseri.projects.cobweb.api.db.impl.SimpleGraph;
import com.mmnaseri.projects.cobweb.api.fs.impl.RootedPathResolver;
import com.mmnaseri.projects.cobweb.domain.content.Edge;
import com.mmnaseri.projects.cobweb.domain.content.Tag;
import com.mmnaseri.projects.cobweb.domain.content.Vertex;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 11:17 PM)
 */
public class Test {

    public static void main(String[] args) {
        FileSystem fs = FileSystems.getDefault();
        final Path systemRoot = fs.getRootDirectories().iterator().next();
        final Path root = systemRoot.resolve(fs.getPath("Users", "milad", "db"));
        final RootedPathResolver pathResolver = new RootedPathResolver(root);
        final SimpleGraph<UUID> graph = new SimpleGraph<>("grf", pathResolver, IdentifierFactory.UUID_IDENTIFIER_FACTORY);
        final Tag<UUID> tag = graph.createTag("tag2", "My sample tag");
        final Vertex<UUID> first = graph.createVertex();
        final Vertex<UUID> second = graph.createVertex();
        final Edge<UUID> edge = graph.createEdge(first, second);
        edge.getTags().add(tag);
        graph.save(edge);
    }

}
