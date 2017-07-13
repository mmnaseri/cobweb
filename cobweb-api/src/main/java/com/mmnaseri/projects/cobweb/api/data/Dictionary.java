package com.mmnaseri.projects.cobweb.api.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 5:28 PM)
 */
public interface Dictionary<K extends Serializable & Comparable<K>> {

    boolean store(K key) throws IOException;

    boolean has(K key);

    boolean delete(K key);

    long count();

    void truncate();

    List<K> keys();

}
