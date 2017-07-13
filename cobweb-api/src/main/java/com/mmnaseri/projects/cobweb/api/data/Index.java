package com.mmnaseri.projects.cobweb.api.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 7:51 PM)
 */
public interface Index<K extends Serializable & Comparable<K>, V extends Serializable> extends Dictionary<K> {

    boolean store(K key, V value) throws IOException;

    V get(K key) throws IOException;

    boolean update(K key, V value) throws IOException;

    boolean upsert(K key, V value) throws IOException;

    List<V> values();

}
