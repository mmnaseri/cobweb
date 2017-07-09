package com.mmnaseri.projects.cobweb.api.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/8/17, 7:51 PM)
 */
public interface Index<I extends Serializable & Comparable<I>, V extends Serializable> {

    void store(I key, V value) throws IOException;

    boolean has(I key);

    V get(I key) throws IOException;

    boolean delete(I key);

    boolean update(I key, V value) throws IOException;

    boolean upsert(I key, V value) throws IOException;

    List<V> all();

    long count();

    void truncate();

}
