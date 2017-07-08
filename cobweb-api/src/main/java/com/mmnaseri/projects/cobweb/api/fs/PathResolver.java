package com.mmnaseri.projects.cobweb.api.fs;

import com.mmnaseri.projects.cobweb.api.db.Graph;
import com.mmnaseri.projects.cobweb.domain.content.ObjectType;
import com.mmnaseri.projects.cobweb.domain.content.Persistent;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 7:29 PM)
 */
public interface PathResolver {

    <I extends Serializable> Path resolve(Graph<I> graph, Persistent<I> persistent);

    <I extends Serializable> Path resolve(Graph<I> graph, ObjectType type, Identifier<I> identifier);

    <I extends Serializable> Path resolve(Graph<I> graph, ObjectType type);

}
