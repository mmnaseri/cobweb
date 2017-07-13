package com.mmnaseri.projects.cobweb.api.graph;

import com.mmnaseri.projects.cobweb.domain.content.Persistent;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 7:29 PM)
 */
public interface Query<K extends Serializable & Comparable<K>, P extends Persistent<K>> {
}
