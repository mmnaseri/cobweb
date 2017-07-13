package com.mmnaseri.projects.cobweb.api.graph;

import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/13/17, 3:23 PM)
 */
public interface GraphConfiguration<K extends Serializable & Comparable<K>> extends Serializable {

    String getName();

    IdentifierFactory<K> getIdentifierFactory();

    IdentifierGenerator<K> getIdentifierGenerator();

}
