package com.mmnaseri.projects.cobweb.api.graph;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/16/17, 1:04 AM)
 */
public interface GraphProvider {

    <K extends Serializable & Comparable<K>> Graph<K, ?> getGraph(String name, Class<K> keyType) throws IOException;

    <K extends Serializable & Comparable<K>, C extends GraphConfiguration<K>> Graph<K, C> save(Graph<K, C> graph) throws IOException;

}
